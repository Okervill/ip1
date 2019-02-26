/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainScreen;

import NewEmployee.AddTherapist;
import Animation.Shaker;
import Login.Login;
import SQL.SQLHandler;
import ViewPatient.ViewPatient;
import ViewTherapist.ViewTherapist;
import integratedproject1.SwitchWindow;
import integratedproject1.User;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
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
    @FXML
    private DatePicker datePicker;
    @FXML
    private ListView<String> displayAllAppointments;
    @FXML
    private ListView<String> displaySpecificAppointments;
    @FXML
    private ChoiceBox<String> therapists;

    String userType;
    String username;
    String selectedTherapist;

    /**
     * Initializes the controller class.
     */
    //initialise sql
    SQLHandler sql = new SQLHandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //This allows setdata to be ran before this section of code
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
            displayAppointments(selectedTherapist);
            //-------------------------------------------------------------------------//
            //add listener to the list view selection to get extra info on appointments//
            //-------------------------------------------------------------------------//
            displayAllAppointments.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                try {
                    displayAppointmentDetails(newValue);
                } catch (SQLException ex) {
                    Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            therapists.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                displayAppointments(newValue);
            });

        });

        searchTherapist.setVisible(false);
        addTherapist.setVisible(false);
        therapists.setVisible(false);
    }

    @FXML
    private void logout(ActionEvent event) {
        System.out.println("Log out Sucessful");
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
        SwitchWindow.switchWindow((Stage) searchTherapist.getScene().getWindow(), new ViewTherapist());
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
    private void pickDate(ActionEvent event) throws SQLException {
        if (!userType.equals("therapist")) {
            selectedTherapist = therapists.getSelectionModel().getSelectedItem();
        } else {
            selectedTherapist = username;
        }
        displayAppointments(selectedTherapist);
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
        displaySpecificAppointments.setItems(info);
    }

    public void displayTherapists() throws SQLException {
        //-----------------------------------------------------------//
        //Add all therapists to a choice box for receptionists to use//
        //-----------------------------------------------------------//
        ArrayList<String> allTherapists = sql.getAllUsernames("therapist");//ReadWriteFile.getUsernames("therapist");
        ObservableList<String> t = FXCollections.observableArrayList();
        //option for viewing all appointments
        t.add("all");
        for (int i = 0; i < allTherapists.size(); i++) {
            t.add(allTherapists.get(i));
        }
        therapists.setItems(t);
        therapists.getSelectionModel().select(0);
        therapists.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            selectedTherapist = newValue;
            displayAppointments(selectedTherapist);
        });
    }

    public void displayAppointments(String user) {

        LocalDate date = datePicker.getValue();
        ArrayList<String> allAppointments = null;
        try {
            if (user.equals("all")) {
                allAppointments = sql.getAllShortAppointments(date);//ReadWriteFile.getShortAppointments(date, user);
            } else {
                allAppointments = sql.getShortAppointments(date, selectedTherapist);//ReadWriteFile.getShortAppointments(date, user);
            }
            //If there are no appointments return
            if (allAppointments.size() < 1) {
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ObservableList<String> appointments = FXCollections.observableArrayList();

        for (int i = 0; i < allAppointments.size(); i++) {
            appointments.add(allAppointments.get(i));
        }
        displayAllAppointments.setItems(appointments);
    }
}
