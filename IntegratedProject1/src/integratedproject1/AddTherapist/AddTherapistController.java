/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integratedproject1.AddTherapist;

import AddTherapist.AddTherapist;
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

/**
 * FXML Controller class
 *
 * @author choco
 */
public class AddTherapistController implements Initializable {

    @FXML
    private ChoiceBox<String> manager;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manager.setItems(FXCollections.observableArrayList("true", "false"));
        manager.getSelectionModel().select("false");
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
        String m = manager.getSelectionModel().getSelectedItem();
        
        Therapist t = new Therapist(f, s, p, m);
        
        SwitchWindow.switchWindow((Stage) save.getScene().getWindow(), new Mainscreen());
    }
    
}
