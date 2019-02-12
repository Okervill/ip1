/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainScreen;

import Login.Login;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import integratedproject1.SwitchWindow;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Andy
 */
public class MainscreenController implements Initializable {

    @FXML
    private Button logout;
    @FXML
    private TextField findPatient;

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
        SwitchWindow.switchWindow((Stage)logout.getScene().getWindow(), new Login());
    }
    
    @FXML
    private void newPatient(ActionEvent event) {
    }
    
    @FXML
    private void newAppointment(ActionEvent event) {
    }
    
    @FXML
    private void addTherapist(ActionEvent event) {
    }
    
    @FXML
    private void findPatient(ActionEvent event) {
    }
}
