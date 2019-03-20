/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NewAppointment;

import SQL.SQLHandler;
import integratedproject1.Appointment;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewAppointmentController implements Initializable {

    @FXML
    private ChoiceBox<String> chooseTherapist;
    @FXML
    private Button close;

    SQLHandler sql = new SQLHandler();
    @FXML
    private DatePicker date;
    @FXML
    private TextField time;
    @FXML
    private ChoiceBox<String> chooseService;
    @FXML
    private Button save;

    String patientNumber;
    @FXML
    private Label appointmentNumber;
    @FXML
    private TextField selectCost;

    /**
     * Initialises the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Get all therapists
        ArrayList<String> allTherapists = new ArrayList<>();
        ObservableList<String> therapists = FXCollections.observableArrayList();
        try {
            allTherapists = sql.getAllUsernames("therapist");
        } catch (SQLException ex) {
            Logger.getLogger(NewAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < allTherapists.size(); i++) {
            therapists.add(allTherapists.get(i));
        }
        chooseTherapist.setItems(therapists);

        //Get all services
        ArrayList<String> allServices = new ArrayList<>();
        ObservableList<String> services = FXCollections.observableArrayList();
        try {
            allServices = sql.getAllServices();
        } catch (SQLException ex) {
            Logger.getLogger(NewAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < allServices.size(); i++) {
            services.add(allServices.get(i));
        }
        chooseService.setItems(services);

    }

    @FXML
    private void saveAppointment(ActionEvent event) throws SQLException {

        if (patientNumber.isEmpty() || chooseTherapist.getSelectionModel().getSelectedItem().isEmpty() || date.getValue().toString().isEmpty() || time.getText().isEmpty() || chooseService.getSelectionModel().getSelectedItem().isEmpty()) {

            //Display error is details are not entered correctly
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Enter details!");
            alert.setContentText("You must enter details in all boxes");
            alert.showAndWait();

            return;
        }
        
        String newT = time.getText();
        try {
            LocalTime.parse(newT);
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Time");
            alert.setContentText("Please input a time of the format HH:MM");
            alert.showAndWait();
            return;
        }
        LocalTime newTime = LocalTime.parse(newT);

        Appointment a = new Appointment(
                patientNumber, // Patient Number
                chooseTherapist.getSelectionModel().getSelectedItem(), // Therapist
                date.getValue(), // Date
                newTime, // Time
                chooseService.getSelectionModel().getSelectedItem(), // Service
                selectCost.getText()
        );

        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    public void setData(String patientNo) {
        patientNumber = patientNo;
    }
}
