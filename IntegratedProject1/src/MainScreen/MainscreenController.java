/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainScreen;

import NewPatient.NewPatient;
import EditAppointment.EditAppointment;
import Login.Login;
import MainscreenSearch.MainscreenSearch;
import ManageHoliday.ManageHoliday;
import Management.Management;
import SQL.SQLHandler;
import integratedproject1.SwitchWindow;
import integratedproject1.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Chris Lowton
 */
public class MainscreenController implements Initializable {

    @FXML
    private Button logout;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ListView<String> mondayAppointments;
    @FXML
    private ListView<String> tuesdayAppointments;
    @FXML
    private ListView<String> wednesdayAppointments;
    @FXML
    private ListView<String> thursdayAppointments;
    @FXML
    private ListView<String> fridayAppointments;
    @FXML
    private ListView<String> saturdayAppointments;
    @FXML
    private ListView<String> sundayAppointments;
    @FXML
    private ChoiceBox<String> therapists;

    public Button close;

    String userType;
    String username;
    String selectedTherapist = "";

    @FXML
    private Text mondayTitle;
    @FXML
    private Text tuesdayTitle;
    @FXML
    private Text wednesdayTitle;
    @FXML
    private Text thursdayTitle;
    @FXML
    private Text fridayTitle;
    @FXML
    private Text saturdayTitle;
    @FXML
    private Text sundayTitle;
    @FXML
    private Button previousWeek;
    @FXML
    private Button nextWeek;
    @FXML
    private Button management;

    /**
     * Initializes the controller class.
     */
    //initialise sql
    SQLHandler sql = new SQLHandler();
    @FXML
    private Button search;
    @FXML
    private Button manageholiday;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        mondayAppointments.getStylesheets().add("/MainScreen/listview.css");
        tuesdayAppointments.getStylesheets().add("/MainScreen/listview.css");
        wednesdayAppointments.getStylesheets().add("/MainScreen/listview.css");
        thursdayAppointments.getStylesheets().add("/MainScreen/listview.css");
        fridayAppointments.getStylesheets().add("/MainScreen/listview.css");
        saturdayAppointments.getStylesheets().add("/MainScreen/listview.css");
        sundayAppointments.getStylesheets().add("/MainScreen/listview.css");

        try {
            setListViewCellWrap();
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Platform.runLater(() -> {
            if (username == null && userType == null) {
                User currentUser = new User();
                username = currentUser.getUsername();
                userType = currentUser.getUserType();
            }

            //------------------------------------//
            //Add all therapists to the choice box//
            //------------------------------------//
            try {
                displayTherapists();
            } catch (SQLException ex) {
                Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (!userType.equals("therapist")) {
                selectedTherapist = therapists.getSelectionModel().getSelectedItem();
            } else {
                selectedTherapist = username;
            }
            //------------------------------------------------//
            //Set date as today and get appointments for today//
            //------------------------------------------------//
            LocalDate today = LocalDate.now();
            datePicker.setValue(today);
            try {
                checkHoliday();
            } catch (SQLException ex) {
                Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    @FXML
    private void logout(ActionEvent event) {
        SwitchWindow.switchWindow((Stage) logout.getScene().getWindow(), new Login());
    }

    @FXML
    private void newPatient(ActionEvent event) throws IOException {
        SwitchWindow.switchWindow((Stage) logout.getScene().getWindow(), new NewPatient());

    }

    @FXML
    private void findPatient(ActionEvent event) {
        SwitchWindow.switchWindow((Stage) logout.getScene().getWindow(), new MainscreenSearch());
        try {
            displayAppointments();
        } catch (SQLException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void checkHoliday() throws SQLException {
            if (userType.equals("manager") && sql.getUnapprovedHoliday("Pending").size() >= 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert!");
                alert.setHeaderText("There are new holidays pending approval!");
                alert.showAndWait();
            }
            if (!userType.equals("manager") && sql.getUpdatedHolidays(username)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert!");
                alert.setHeaderText("Your holidays have been updated!");
                alert.showAndWait();
            }
    }

    public void setData(String u, String t) {

        userType = t;
        username = u;

        management.setVisible(false);
        therapists.setVisible(false);

        if (userType.equals("manager")) {
            management.setVisible(true);
            therapists.setVisible(true);
        }
        if (userType.equals("receptionist")) {
            therapists.setVisible(true);
        }
    }

    @FXML //Get appointments for selected dates
    private void pickDate(ActionEvent event) throws IOException, SQLException {
        displayAppointments();
    }

    //Get full appointment details for selected appointment
    private void displayAppointmentDetails(String appointment) throws SQLException {

        //Check for null/empty
        if (appointment == null || appointment.isEmpty() || appointment.length() < 8) {
            return;
        }

        //Get Appointment Number
        int first = appointment.indexOf(": ") + 1;
        int second = appointment.indexOf(": ", first) + 1;
        int start = appointment.indexOf(": ", second) + 2;
        int stop = appointment.indexOf("\n", start);

        String appointmentNumber = appointment.substring(start, stop);

        SwitchWindow.switchWindow((Stage) nextWeek.getScene().getWindow(), new EditAppointment(appointmentNumber));
        mondayAppointments.getSelectionModel().clearSelection();
        tuesdayAppointments.getSelectionModel().clearSelection();
        wednesdayAppointments.getSelectionModel().clearSelection();
        thursdayAppointments.getSelectionModel().clearSelection();
        fridayAppointments.getSelectionModel().clearSelection();
        saturdayAppointments.getSelectionModel().clearSelection();
        sundayAppointments.getSelectionModel().clearSelection();
        displayAppointments();
        try {
            setListViewCellWrap();
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void displayTherapists() throws SQLException {
        //-----------------------------------------------------------//
        //Add all therapists to a choice box for receptionists to use//
        //-----------------------------------------------------------//
        ArrayList<String> allTherapists = sql.getAllUsernames("therapist");
        ObservableList<String> t = FXCollections.observableArrayList();
        //option for viewing all appointments
        t.add("All");
        for (int i = 0; i < allTherapists.size(); i++) {
            t.add(allTherapists.get(i));
        }
        therapists.setItems(t);
        therapists.getSelectionModel().select(0);
        therapists.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            selectedTherapist = newValue;
            if (selectedTherapist == null || selectedTherapist.isEmpty() || selectedTherapist.toUpperCase().equals("ALL")) {
                selectedTherapist = "";
            }
            try {
                displayAppointments();
            } catch (SQLException ex) {
                Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void displayAppointments() throws SQLException {
        LocalDate date = datePicker.getValue();
        if (selectedTherapist.toUpperCase().equals("ALL")) {
            selectedTherapist = "";
        }
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        LocalDate startDate = date.minusDays(dayOfWeek.getValue() - 1);
        LocalDate endDate = date.plusDays(7 - dayOfWeek.getValue());

        mondayTitle.setText("Monday " + startDate.getDayOfMonth() + "/" + startDate.getMonthValue());
        tuesdayTitle.setText("Tuesday " + startDate.plusDays(1).getDayOfMonth() + "/" + startDate.plusDays(1).getMonthValue());
        wednesdayTitle.setText("Wednesday " + startDate.plusDays(2).getDayOfMonth() + "/" + startDate.plusDays(2).getMonthValue());
        thursdayTitle.setText("Thursday " + startDate.plusDays(3).getDayOfMonth() + "/" + startDate.plusDays(3).getMonthValue());
        fridayTitle.setText("Friday " + startDate.plusDays(4).getDayOfMonth() + "/" + startDate.plusDays(4).getMonthValue());
        saturdayTitle.setText("Saturday " + startDate.plusDays(5).getDayOfMonth() + "/" + startDate.plusDays(5).getMonthValue());
        sundayTitle.setText("Sunday " + startDate.plusDays(6).getDayOfMonth() + "/" + startDate.plusDays(6).getMonthValue());

        for (LocalDate i = startDate; i.getDayOfMonth() != endDate.plusDays(1).getDayOfMonth() || i.getMonth() != endDate.plusDays(1).getMonth(); i = i.plusDays(1)) {
            ArrayList<String> allAppointments;
            ObservableList<String> appointments = FXCollections.observableArrayList();
            if (selectedTherapist.isEmpty()) {
                allAppointments = sql.getAllShortAppointments(i);
            } else {
                allAppointments = sql.getShortAppointments(i, selectedTherapist);
            }
            for (int x = 0; x < allAppointments.size(); x++) {
                appointments.add(allAppointments.get(x));
            }

            DayOfWeek loopDayOfWeek = i.getDayOfWeek();

            switch (loopDayOfWeek) {
                case MONDAY:
                    mondayAppointments.setItems(appointments);
                    break;
                case TUESDAY:
                    tuesdayAppointments.setItems(appointments);
                    break;
                case WEDNESDAY:
                    wednesdayAppointments.setItems(appointments);
                    break;
                case THURSDAY:
                    thursdayAppointments.setItems(appointments);
                    break;
                case FRIDAY:
                    fridayAppointments.setItems(appointments);
                    break;
                case SATURDAY:
                    saturdayAppointments.setItems(appointments);
                    break;
                case SUNDAY:
                    sundayAppointments.setItems(appointments);
                    break;
            }
        }
        try {
            setListViewCellWrap();
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addAllDescendents(Parent parent, ArrayList<Node> nodes) {

        for (Node node : parent.getChildrenUnmodifiable()) {
            nodes.add(node);
            if (node instanceof Parent) {
                addAllDescendents((Parent) node, nodes);
            }
        }
    }

    public ArrayList<Node> getAllNodes(Parent root) {
        ArrayList<Node> nodes = new ArrayList<>();
        addAllDescendents(root, nodes);
        return nodes;
    }

    private void setListViewCellWrap() throws IOException {

        ArrayList<ListView> lists = new ArrayList<>();
        lists.add(mondayAppointments);
        lists.add(tuesdayAppointments);
        lists.add(wednesdayAppointments);
        lists.add(thursdayAppointments);
        lists.add(fridayAppointments);
        lists.add(saturdayAppointments);
        lists.add(sundayAppointments);

        lists.forEach((list) -> {
            list.setCellFactory(param -> new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                        setText(null);
                        // other stuff to do...

                    } else {

                        // allow wrapping
                        setMinWidth(list.getWidth());
                        setMaxWidth(list.getWidth());
                        setPrefWidth(list.getWidth());

                        setWrapText(true);

                        setText(item);
                        int start = item.indexOf("Service: ") + 9;
                        String serviceName = item.substring(start, item.length());

                        String appointmentNumber = item.substring(item.indexOf("Appointment: ") + 13, item.indexOf("Service: ") - 1);

                        //set colours per service
                        try {
                            setStyle("-fx-background-color:#" + sql.getServiceColour(serviceName).get(0).substring(2, 8));
                        } catch (SQLException ex) {
                            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        //If cancelled set red
                        try {
                            if (sql.search("appointment", "appointmentnumber", appointmentNumber).get(7).contains("cancelled")) {
                                setStyle("-fx-background-color:red");
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        this.itemProperty().addListener((obs, oldItem, newItem) -> {
                            if (newItem == null) {
                                setStyle("-fx-background-colour:white");
                            }
                        });

                    }
                }
            });
        });
    }

    @FXML
    private void mondayMouseClick(javafx.scene.input.MouseEvent event) throws SQLException {
        displayAppointmentDetails(mondayAppointments.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void tuesdayMouseClick(javafx.scene.input.MouseEvent event) throws SQLException {
        displayAppointmentDetails(tuesdayAppointments.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void wednesdayMouseClick(javafx.scene.input.MouseEvent event) throws SQLException {
        displayAppointmentDetails(wednesdayAppointments.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void thursdayMouseClick(javafx.scene.input.MouseEvent event) throws SQLException {
        displayAppointmentDetails(thursdayAppointments.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void fridayMouseClick(javafx.scene.input.MouseEvent event) throws SQLException {
        displayAppointmentDetails(fridayAppointments.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void saturdayMouseClick(javafx.scene.input.MouseEvent event) throws SQLException {
        displayAppointmentDetails(saturdayAppointments.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void sundayMouseClick(javafx.scene.input.MouseEvent event) throws SQLException {
        displayAppointmentDetails(sundayAppointments.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void goToPreviousWeek(ActionEvent event) {
        datePicker.setValue(datePicker.getValue().minusDays(7));
    }

    @FXML
    private void goToNextWeek(ActionEvent event) {
        datePicker.setValue(datePicker.getValue().plusDays(7));
    }

    //-----------------------------------------//
    // Search appointments for given ap number //
    //-----------------------------------------//
    public ArrayList<String> searchAppointments(String appointmentNumber) throws SQLException {

        ArrayList<String> appointment = sql.search("appointment", "appointmentnumber", appointmentNumber);

        return appointment;
    }

    @FXML
    private void openManagementOptions(ActionEvent event) throws SQLException, IOException {
        SwitchWindow.switchWindow((Stage) management.getScene().getWindow(), new Management());
        try {
            setListViewCellWrap();
            displayTherapists();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to load therapist usernames");
            alert.setContentText("Error connecting to database, unable to load therapists");
            alert.showAndWait();
        }
    }

    @FXML
    private void manageHoliday(ActionEvent event) {
        SwitchWindow.switchWindow((Stage) manageholiday.getScene().getWindow(), new ManageHoliday(username));
    }
}
