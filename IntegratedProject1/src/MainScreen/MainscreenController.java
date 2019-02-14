/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainScreen;

import AddTherapist.AddTherapist;
import Login.Login;
import ViewTherapist.ViewTherapist;
import ViewTherapist.ViewTherapistController;
import integratedproject1.ReadWriteFile;
import integratedproject1.SwitchWindow;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private void newAppointment(ActionEvent event) {
    }

    @FXML
    private void addTherapist(ActionEvent event) {
        SwitchWindow.switchWindow((Stage) addTherapist.getScene().getWindow(), new AddTherapist());
    }

    @FXML
    private void findPatient(ActionEvent event) {
    }

    @FXML
    private void findTherapist(ActionEvent event) throws IOException {
        SwitchWindow.switchWindow((Stage) searchTherapist.getScene().getWindow(), new ViewTherapist());
    }
}
