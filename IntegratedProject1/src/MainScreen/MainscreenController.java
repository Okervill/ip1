/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainScreen;

import AddTherapist.AddTherapist;
import Login.Login;
import ViewPatient.ViewPatient;
import ViewTherapist.ViewTherapist;
import integratedproject1.ReadWriteFile;
import integratedproject1.SwitchWindow;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO if user isn't a manager hide the add therapist button
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
        ArrayList<String> patientData = search();
        if (patientData != null) {
            SwitchWindow.switchWindow((Stage) searchTherapist.getScene().getWindow(), new ViewPatient(patientData));
        }
    }

    @FXML
    private void findTherapist(ActionEvent event) throws IOException {
        SwitchWindow.switchWindow((Stage) searchTherapist.getScene().getWindow(), new ViewTherapist("Test"));
    }

    @FXML
    private void viewPatient(ActionEvent event) {
        SwitchWindow.switchWindow((Stage) searchTherapist.getScene().getWindow(), new ViewPatient(new ArrayList<String>()));
    }

    @FXML
    private ArrayList<String> search() {

        String currentUsername = findPatient.getText();

        ArrayList<String> patient = null;
        try {
            patient = ReadWriteFile.getPatientData(currentUsername);
            if (patient.size() < 8) {
                return null;
            }
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return patient;
    }
}
