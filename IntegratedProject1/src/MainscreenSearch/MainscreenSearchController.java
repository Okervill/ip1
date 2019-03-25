/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainscreenSearch;

import MainScreen.MainscreenController;
import SQL.SQLHandler;
import ViewPatient.ViewPatient;
import integratedproject1.SwitchWindow;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author patrick
 */
public class MainscreenSearchController implements Initializable {

    @FXML
    private TextField getFirstname;
    @FXML
    private TextField getPatientNumber;
    @FXML
    private TextField getPostcode;
    @FXML
    private TextField getSurname;
    @FXML
    private Button closeScene;

    SQLHandler sql = new SQLHandler();
    @FXML
    private ListView<String> patientDisplay;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void handleKeyPress(KeyEvent e) throws SQLException {
        Platform.runLater(() -> {

            ObservableList<String> displayPatientInfo = FXCollections.observableArrayList();

            if (getPatientNumber.getText().isEmpty() && getFirstname.getText().isEmpty() && getSurname.getText().isEmpty() && getPostcode.getText().isEmpty()) {
                patientDisplay.getSelectionModel().clearSelection();
                return;
            }
            if (!getPatientNumber.getText().isEmpty()) {
                String patientNumber = getPatientNumber.getText();
                ArrayList<String> patientInfo = null;

                try {
                    patientInfo = sql.search("patient", "patientnumber", patientNumber);
                } catch (SQLException ex) {
                    Logger.getLogger(MainscreenSearchController.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (patientInfo == null || patientInfo.isEmpty()) {
                    return;
                }

                for (int i = 0; i < patientInfo.size(); i = i + 8) {
                    displayPatientInfo.add(patientInfo.get(i + 7) + " " + patientInfo.get(i) + " " + patientInfo.get(i + 1) + " DOB: " + patientInfo.get(i + 4) + " Mobile: " + patientInfo.get(3));
                }

                if (!displayPatientInfo.isEmpty()) {
                    patientDisplay.setItems(displayPatientInfo);
                }

            }

            ArrayList<String> searchQuery = new ArrayList<>();
            ArrayList<String> patientInfo = null;

            if (getFirstname.getText().isEmpty()) {
                searchQuery.add(" ");
            } else {
                searchQuery.add(getFirstname.getText());
            }

            if (getSurname.getText().isEmpty()) {
                searchQuery.add(" ");
            } else {
                searchQuery.add(getSurname.getText());
            }

            if (getPostcode.getText().isEmpty()) {
                searchQuery.add(" ");
            } else {
                searchQuery.add(getPostcode.getText());
            }

            try {
                patientInfo = sql.searchPatientDetails(searchQuery);
            } catch (SQLException ex) {
                Logger.getLogger(MainscreenSearchController.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (patientInfo == null) {
                return;
            }

            int patientsFound = 0;

            try {
                patientsFound = patientInfo.size() / sql.countFields("patient");
            } catch (SQLException ex) {
                Logger.getLogger(MainscreenSearchController.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (int i = 0; i < patientInfo.size(); i = i + 8) {
                displayPatientInfo.add(patientInfo.get(i + 7) + " " + patientInfo.get(i) + " " + patientInfo.get(i + 1) + " DOB: " + patientInfo.get(i + 4) + " Mobile: " + patientInfo.get(3));
            }

            if (!displayPatientInfo.isEmpty()) {
                patientDisplay.setItems(displayPatientInfo);
            }

        });
    }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) closeScene.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void patientSelected(MouseEvent event) {
        if (patientDisplay.getSelectionModel().isEmpty()) return;
        String patientNumber = patientDisplay.getSelectionModel().getSelectedItem().substring(0, patientDisplay.getSelectionModel().getSelectedItem().indexOf(" "));

        ArrayList<String> patient = null;
        try {
            patient = sql.search("patient", "patientnumber", patientNumber);
        } catch (SQLException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (patient != null) {
            SwitchWindow.switchWindow((Stage) patientDisplay.getScene().getWindow(), new ViewPatient(patient));
            patientDisplay.getSelectionModel().clearSelection();
        }
    }

}
