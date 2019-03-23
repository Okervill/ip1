/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewPatient;

import EditAppointment.EditAppointment;
import NewAppointment.NewAppointment;
import SQL.SQLHandler;
import integratedproject1.SwitchWindow;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ViewPatientController implements Initializable {

    @FXML
    private TextField firstname;
    @FXML
    private ChoiceBox<String> gender;
    @FXML
    private DatePicker dob;
    @FXML
    private TextField mobno;
    @FXML
    private TextField email;
    @FXML
    private TextField surname;
    @FXML
    private TextField postcode;
    @FXML
    private Label patientNo;
    @FXML
    private Button newAppointmentButton;
    @FXML
    private Button back;

    SQLHandler sql = new SQLHandler();
    @FXML
    private ListView<String> appointmentDisplay;
    @FXML
    private Button save;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        addColour();
        Platform.runLater(() -> {
            try {
                getAppointments();
            } catch (SQLException ex) {
                Logger.getLogger(ViewPatientController.class.getName()).log(Level.SEVERE, null, ex);
            }

            appointmentDisplay.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                try {
                    displayFullAppointment(newValue);
                } catch (SQLException ex) {
                    Logger.getLogger(ViewPatientController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        });
    }

    public void setData(ArrayList<String> data) {
        firstname.setText(data.get(0));
        surname.setText(data.get(1));
        email.setText(data.get(2));
        mobno.setText(data.get(3));
        dob.setValue(LocalDate.parse(data.get(4)));
        gender.setItems(FXCollections.observableArrayList("Male", "Female"));
        gender.getSelectionModel().select(data.get(5));
        postcode.setText(data.get(6));
        patientNo.setText(data.get(7));
    }

    @FXML
    private void newAppointment(ActionEvent event) throws IOException {

        SwitchWindow.switchWindow((Stage) newAppointmentButton.getScene().getWindow(), new NewAppointment(patientNo.getText()));
        try {
            getAppointments();
        } catch (SQLException ex) {
            Logger.getLogger(ViewPatientController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void back(ActionEvent event) {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }

    public void getAppointments() throws SQLException {
        ArrayList<String> allAppointments = sql.getPatientAppointments(patientNo.getText());
        ObservableList<String> appointments = FXCollections.observableArrayList();

        for (int i = 0; i < allAppointments.size(); i++) {
            appointments.add(allAppointments.get(i));
        }

        appointmentDisplay.setItems(appointments);

    }

    public void displayFullAppointment(String appointment) throws SQLException {

        if (appointment == null || appointment.isEmpty() || appointment.length() < 8) {
            return;
        }

        //Get Appointment Number
        int first = appointment.indexOf(": ") + 1;
        int second = appointment.indexOf(": ", first) + 1;
        int start = appointment.indexOf(": ", second) + 2;
        int stop = appointment.indexOf(" ", start);

        String appointmentNumber = appointment.substring(start, stop);

        SwitchWindow.switchWindow((Stage) back.getScene().getWindow(), new EditAppointment(appointmentNumber));
        getAppointments();
    }

    @FXML
    private void save(ActionEvent event) {
        String fname = firstname.getText();
        String sname = surname.getText();
        String gen = gender.getSelectionModel().getSelectedItem();
        LocalDate dateob = dob.getValue();
        String em = email.getText();
        String mob = mobno.getText();
        String pcode = postcode.getText();

        if (fname.isEmpty() || sname.isEmpty() || gen.isEmpty() || em.isEmpty() || mob.isEmpty() || pcode.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Details missing");
            alert.setContentText("Please ensure all details are entered and correct");
            alert.showAndWait();
            return;
        }

        try {
            sql.updatePatient(fname, sname, em, mob, dateob, gen, pcode, patientNo.getText());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Save Sucessful");
            alert.showAndWait();
        } catch (SQLException ex) {
            Logger.getLogger(ViewPatientController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Connection Error");
            alert.setContentText("Unable to connect to database, please try again later");
            alert.showAndWait();
        }
    }

    public void addColour() {
        appointmentDisplay.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                    // other stuff to do...

                } else {
                    
                    if (item.contains("cancelled")) {
                        setStyle("-fx-background-color:red");
                        return;
                    }

                    setText(item);
                    if (item.contains("Sports Massage")) {
                        setStyle("-fx-background-color:#ccffcc");
                    } else if (item.contains("Physiotherapy")) {
                        setStyle("-fx-background-color:#d9b3ff");
                    } else if (item.contains("Acupuncture")) {
                        setStyle("-fx-background-color:#b3e0ff");
                    } else if (item.contains("Hairdressing")) {
                        setStyle("-fx-background-color:#fccf64");
                    }
                }
            }
        });
    }
}
