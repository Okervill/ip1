/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddTherapist;

import MainScreen.Mainscreen;
import integratedproject1.SwitchWindow;
import integratedproject1.Therapist;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AddTherapistController implements Initializable {

    @FXML
    private Button discard;
    @FXML
    private Button save;
    @FXML
    private TextField surname;
    @FXML
    private TextField password;
    @FXML
    private TextField firstname;
    @FXML
    private ChoiceBox<String> type;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        type.setItems(FXCollections.observableArrayList("manager", "therapist", "receptionist"));
    }    

    @FXML
    private void discard(ActionEvent event) {
        SwitchWindow.switchWindow((Stage) discard.getScene().getWindow(), new Mainscreen());
    }

    @FXML
    private void save(ActionEvent event) throws IOException {
        String f = firstname.getText();
        String s = surname.getText();
        String p = password.getText();
        String m = type.getSelectionModel().getSelectedItem();
        
        Therapist t = new Therapist(f, s, p, m);
        
        SwitchWindow.switchWindow((Stage) save.getScene().getWindow(), new Mainscreen());
    }
    
}