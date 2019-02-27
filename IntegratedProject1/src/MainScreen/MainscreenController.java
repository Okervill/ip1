/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainScreen;

import Animation.Shaker;
import Login.Login;
import SQL.SQLHandler;
import ViewPatient.ViewPatient;
import integratedproject1.ReadWriteFile;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
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
    private TextField findPatient;
    @FXML
    private Button searchTherapist;
    @FXML
    private Button addTherapist;
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
    private ListView<String> appointmentDetails;
    @FXML
    private ChoiceBox<String> therapists;

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

    /**
     * Initializes the controller class.
     */
    //initialise sql
    SQLHandler sql = new SQLHandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //This allows setdata to be ran before this section of code
        setListViewCellWrap();
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

            //------------------------------------------------//
            //Set date as today and get appointments for today//
            //------------------------------------------------//
            LocalDate today = LocalDate.now();
            datePicker.setValue(today);

            if (!userType.equals("therapist")) {
                selectedTherapist = therapists.getSelectionModel().getSelectedItem();
            } else {
                selectedTherapist = username;
            }
            //-------------------------------------------------------------------------//
            //add listener to the list view selection to get extra info on appointments//
            //-------------------------------------------------------------------------//
        });
    }

    @FXML
    private void logout(ActionEvent event) {
        SwitchWindow.switchWindow((Stage) logout.getScene().getWindow(), new Login());
    }

    @FXML
    private void newPatient(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddPatient/NewPatient.fxml"));
        Parent root = (Parent) loader.load();
        Stage secondStage = new Stage();
        secondStage.setScene(new Scene(new HBox(root)));

        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.showAndWait();
    }

    @FXML
    private void addTherapist(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/NewEmployee/AddTherapist.fxml"));
        Parent root = (Parent) loader.load();
        Stage secondStage = new Stage();
        secondStage.setScene(new Scene(new HBox(root)));

        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.showAndWait();
        //SwitchWindow.switchWindow((Stage) logout.getScene().getWindow(), new AddTherapist());
    }

    @FXML
    private void findPatient(ActionEvent event) {

        if (findPatient.getText().length() < 1) {
            return;
        }

        ArrayList<String> patientData = search();
        if (patientData != null) {
            SwitchWindow.switchWindow((Stage) searchTherapist.getScene().getWindow(), new ViewPatient(patientData));
        }
    }

    @FXML
    private void findTherapist(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewTherapist/ViewTherapist.fxml"));
        Parent root = (Parent) loader.load();
        Stage secondStage = new Stage();
        secondStage.setScene(new Scene(new HBox(root)));

        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.showAndWait();
        //SwitchWindow.switchWindow((Stage) searchTherapist.getScene().getWindow(), new ViewTherapist());
    }
    
    private ArrayList<String> search() {

        String patientNumber = findPatient.getText();

        ArrayList<String> patient = null;
        try {
            patient = sql.search("patient", "patientnumber", patientNumber);//ReadWriteFile.getPatientData(currentUsername);
            if (patient.size() < 8) {

                Shaker shaker = new Shaker(findPatient);
                shaker.shake();
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return patient;
    }

    public void setData(String u, String t) {

        userType = t;
        username = u;

        searchTherapist.setVisible(false);
        addTherapist.setVisible(false);
        therapists.setVisible(false);

        if (userType.equals("manager")) {
            searchTherapist.setVisible(true);
            addTherapist.setVisible(true);
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
        int start = appointment.indexOf(" ") + 1;
        int stop = appointment.indexOf(" ", start + 1);
        String appointmentNumber = appointment.substring(start, stop);

        ArrayList<String> details = sql.search("appointment", "appointmentnumber", appointmentNumber); //ReadWriteFile.getAppointmentNumberInfo(appointmentNumber);
        ObservableList<String> info = FXCollections.observableArrayList();
        for (int i = 0; i < details.size(); i++) {
            info.add((String) details.get(i));
        }
        appointmentDetails.setItems(info);
    }

    public void displayTherapists() throws SQLException {
        //-----------------------------------------------------------//
        //Add all therapists to a choice box for receptionists to use//
        //-----------------------------------------------------------//
        ArrayList<String> allTherapists = sql.getAllUsernames("therapist");//ReadWriteFile.getUsernames("therapist");
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
            if (selectedTherapist.toUpperCase().equals("ALL")) {
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
        
        appointmentDetails.getItems().clear();
        
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
                    mondayAppointments.getSelectionModel().selectFirst();
                    break;
                case TUESDAY:
                    tuesdayAppointments.setItems(appointments);
                    tuesdayAppointments.getSelectionModel().selectFirst();
                    break;
                case WEDNESDAY:
                    wednesdayAppointments.setItems(appointments);
                    wednesdayAppointments.getSelectionModel().selectFirst();
                    break;
                case THURSDAY:
                    thursdayAppointments.setItems(appointments);
                    thursdayAppointments.getSelectionModel().selectFirst();
                    break;
                case FRIDAY:
                    fridayAppointments.setItems(appointments);
                    fridayAppointments.getSelectionModel().selectFirst();
                    break;
                case SATURDAY:
                    saturdayAppointments.setItems(appointments);
                    saturdayAppointments.getSelectionModel().selectFirst();
                    break;
                case SUNDAY:
                    sundayAppointments.setItems(appointments);
                    sundayAppointments.getSelectionModel().selectFirst();
                    break;
            }
        }
    }
    
    
    
    
    
    private void setListViewCellWrap() {
        mondayAppointments.setCellFactory(param -> new ListCell<String>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item==null) {
                    setGraphic(null);
                    setText(null); 
                    // other stuff to do...

                }else{

                    // set the width's
                    setMinWidth(param.getWidth());
                    setMaxWidth(param.getWidth());
                    setPrefWidth(param.getWidth());

                    // allow wrapping
                    setWrapText(true);

                    setText(item.toString());


                }
            }
        });
        tuesdayAppointments.setCellFactory(param -> new ListCell<String>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item==null) {
                    setGraphic(null);
                    setText(null); 
                    // other stuff to do...

                }else{

                    // set the width's
                    setMinWidth(param.getWidth());
                    setMaxWidth(param.getWidth());
                    setPrefWidth(param.getWidth());

                    // allow wrapping
                    setWrapText(true);

                    setText(item.toString());


                }
            }
        });
        wednesdayAppointments.setCellFactory(param -> new ListCell<String>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item==null) {
                    setGraphic(null);
                    setText(null); 
                    // other stuff to do...

                }else{

                    // set the width's
                    setMinWidth(param.getWidth());
                    setMaxWidth(param.getWidth());
                    setPrefWidth(param.getWidth());

                    // allow wrapping
                    setWrapText(true);

                    setText(item.toString());


                }
            }
        });
        thursdayAppointments.setCellFactory(param -> new ListCell<String>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item==null) {
                    setGraphic(null);
                    setText(null); 
                    // other stuff to do...

                }else{

                    // set the width's
                    setMinWidth(param.getWidth());
                    setMaxWidth(param.getWidth());
                    setPrefWidth(param.getWidth());

                    // allow wrapping
                    setWrapText(true);

                    setText(item.toString());


                }
            }
        });
        fridayAppointments.setCellFactory(param -> new ListCell<String>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item==null) {
                    setGraphic(null);
                    setText(null); 
                    // other stuff to do...

                }else{

                    // set the width's
                    setMinWidth(param.getWidth());
                    setMaxWidth(param.getWidth());
                    setPrefWidth(param.getWidth());

                    // allow wrapping
                    setWrapText(true);

                    setText(item.toString());


                }
            }
        });
        saturdayAppointments.setCellFactory(param -> new ListCell<String>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item==null) {
                    setGraphic(null);
                    setText(null); 
                    // other stuff to do...

                }else{

                    // set the width's
                    setMinWidth(param.getWidth());
                    setMaxWidth(param.getWidth());
                    setPrefWidth(param.getWidth());

                    // allow wrapping
                    setWrapText(true);

                    setText(item.toString());


                }
            }
        });
        sundayAppointments.setCellFactory(param -> new ListCell<String>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item==null) {
                    setGraphic(null);
                    setText(null); 
                    // other stuff to do...

                }else{

                    // set the width's
                    setMinWidth(param.getWidth());
                    setMaxWidth(param.getWidth());
                    setPrefWidth(param.getWidth());

                    // allow wrapping
                    setWrapText(true);

                    setText(item.toString());


                }
            }
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
}
