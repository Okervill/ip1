/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainScreen;

import AddTherapist.AddTherapist;
import Animation.Shaker;
import Login.Login;
import ViewPatient.ViewPatient;
import ViewTherapist.ViewTherapist;
import integratedproject1.ReadWriteFile;
import integratedproject1.SwitchWindow;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainscreenController implements Initializable {

    @FXML
    private Button logout;
    @FXML
    private TextField findPatient;
    @FXML
    private Button searchTherapist;
    @FXML
    private Button addTherapist;

    boolean manager = false;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ListView<String> displayAllAppointments;
    @FXML
    private ListView<String> displaySpecificAppointments;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //------------------------------------------------//
        //Set date as today and get appointments for today//
        //------------------------------------------------//
        LocalDate today = LocalDate.now();
        datePicker.setValue(today);
        LocalDate date = datePicker.getValue();
        ArrayList<String> allAppointments = null;
        try {
            allAppointments = ReadWriteFile.getShortAppointments(date);
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList<String> appointments = FXCollections.observableArrayList();

        for (int i = 0; i < allAppointments.size(); i++) {
            appointments.add(allAppointments.get(i));
        }
        displayAllAppointments.setItems(appointments);
        //-------------------------------------------------------------------------//
        //add listener to the list view selection to get extra info on appointments//
        //-------------------------------------------------------------------------//
        displayAllAppointments.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    displayAppointmentDetails(newValue);
                } catch (IOException ex) {
                    Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    @FXML
    private void logout(ActionEvent event) {
        System.out.println("Log out Sucessful");
        SwitchWindow.switchWindow((Stage) logout.getScene().getWindow(), new Login());
    }

    @FXML
    private void newPatient(ActionEvent event) {
    }

    @FXML
    private void addTherapist(ActionEvent event) {
        SwitchWindow.switchWindow((Stage) addTherapist.getScene().getWindow(), new AddTherapist());
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
        SwitchWindow.switchWindow((Stage) searchTherapist.getScene().getWindow(), new ViewTherapist());
    }

    @FXML
    private void viewPatient(ActionEvent event) {
        SwitchWindow.switchWindow((Stage) searchTherapist.getScene().getWindow(), new ViewPatient(new ArrayList<>()));
    }

    @FXML
    private ArrayList<String> search() {

        String currentUsername = findPatient.getText();

        ArrayList<String> patient = null;
        try {
            patient = ReadWriteFile.getPatientData(currentUsername);
            if (patient.size() < 8) {

                Shaker shaker = new Shaker(findPatient);
                shaker.shake();
                return null;
            }
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return patient;
    }

    public void setData(boolean m) {
        searchTherapist.setVisible(false);
        addTherapist.setVisible(false);
        if (m) {
            searchTherapist.setVisible(true);
            addTherapist.setVisible(true);
        }
    }

    @FXML //Get appointments for selected dates
    private void pickDate(ActionEvent event) throws IOException {
        //get appointments for date
        LocalDate date = datePicker.getValue();
        ArrayList<String> allAppointments = ReadWriteFile.getShortAppointments(date);
        ObservableList<String> appointments = FXCollections.observableArrayList();

        for (int i = 0; i < allAppointments.size(); i++) {
            appointments.add(allAppointments.get(i));
        }
        displayAllAppointments.setItems(appointments);
    }
    
    //Get full appointment details for selected appointment
    private void displayAppointmentDetails(String appointment) throws IOException {
        int start = appointment.indexOf(" ") + 1;
        int stop = appointment.indexOf(" ", start + 1);
        String appointmentNumber = appointment.substring(start, stop);

        ArrayList<String> details = ReadWriteFile.getAppointmentNumberInfo(appointmentNumber);
        ObservableList<String> info = FXCollections.observableArrayList();
        for (int i = 0; i < details.size(); i++) {
            info.add((String) details.get(i));
        }
        displaySpecificAppointments.setItems(info);
    }
}
