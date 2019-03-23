/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EditAppointment;

import NewAppointment.NewAppointmentController;
import SQL.SQLHandler;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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

public class EditAppointmentController implements Initializable {

    @FXML
    private Label appointmentNumber;
    @FXML
    private Label patientNumber;
    @FXML
    private ChoiceBox<String> selectTherapist;
    @FXML
    private DatePicker selectDate;
    @FXML
    private TextField selectTime;
    @FXML
    private ChoiceBox<String> selectService;
    @FXML
    private TextField selectCost;
    @FXML
    private ChoiceBox<String> selectStatus;
    @FXML
    private Button close;
    @FXML
    private Button saveChanges;

    ArrayList<String> appointmentInfo;
    SQLHandler sql = new SQLHandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setData(String a) {

        //Get All Appointment Info
        try {
            appointmentInfo = sql.search("appointment", "appointmentnumber", a);
        } catch (SQLException ex) {
            Logger.getLogger(EditAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }

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

        //Get appointment status
        ObservableList<String> status = FXCollections.observableArrayList();
        status.add("pending");
        status.add("in progress");
        status.add("complete");
        status.add("cancelled - therapist");
        status.add("cancelled - patient");

        appointmentNumber.setText(appointmentInfo.get(0));
        patientNumber.setText(appointmentInfo.get(1));

        selectTherapist.setItems(therapists);
        selectTherapist.getSelectionModel().select(appointmentInfo.get(2));

        selectService.setItems(services);
        selectService.getSelectionModel().select(appointmentInfo.get(5));

        LocalDate date = LocalDate.parse(appointmentInfo.get(3));
        selectDate.setValue(date);

        selectTime.setText(appointmentInfo.get(4));
        selectCost.setText(appointmentInfo.get(6));

        selectStatus.setItems(status);
        selectStatus.getSelectionModel().select(appointmentInfo.get(7));

    }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void saveChanges(ActionEvent event) {

        boolean changes = false;

        String newTherapist = selectTherapist.getSelectionModel().getSelectedItem();
        if (!appointmentInfo.get(2).equals(newTherapist)) {
            changes = true;
        }

        String newService = selectService.getSelectionModel().getSelectedItem();
        if (!appointmentInfo.get(5).equals(newService)) {
            changes = true;
        }

        LocalDate newDate = selectDate.getValue();

        if (!appointmentInfo.get(3).equals(newDate.toString()) && newDate.isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Date");
            alert.setContentText("The selected date is in the past");
            alert.showAndWait();
            return;
        } else if (!appointmentInfo.get(3).equals(newDate.toString())) {
            changes = true;
        }

        String newT = selectTime.getText();
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
        if (!appointmentInfo.get(4).equals(newT)) {
            changes = true;
        }

        String newCost = selectCost.getText();
        if (!appointmentInfo.get(6).equals(newCost)) {
            changes = true;
        }

        String newStatus = selectStatus.getSelectionModel().getSelectedItem();
        if (!appointmentInfo.get(7).equals(newStatus)) {
            changes = true;
        }

        if (!changes) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save");
            alert.setHeaderText("No changes made");
            alert.setContentText("No changes have been made");
            alert.showAndWait();
        } else {
            try {
                sql.updateAppointment(appointmentInfo.get(0), newTherapist, newDate, newTime, newService, newCost, newStatus);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Save");
                alert.setHeaderText("Save successful");
                alert.setContentText("Appointment updated successfully");
                alert.showAndWait();

                Stage stage = (Stage) close.getScene().getWindow();
                stage.close();

            } catch (SQLException ex) {
                Logger.getLogger(EditAppointmentController.class.getName()).log(Level.SEVERE, null, ex);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Saving");
                alert.setContentText("Unable to update appointment, please try again");
                alert.showAndWait();
            }
        }
    }
}
