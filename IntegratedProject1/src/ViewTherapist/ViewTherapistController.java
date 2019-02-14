/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewTherapist;

import MainScreen.Mainscreen;
import MainScreen.MainscreenController;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ViewTherapistController implements Initializable {

    @FXML
    private TextField firstname;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField surname;
    @FXML
    private Button save;
    @FXML
    private Button discard;
    @FXML
    private TextField currentUser;
    @FXML
    private Button search;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void save(ActionEvent event) throws IOException {
        
        String currentUsername = currentUser.getText();
        
        ArrayList<String> current = null;
        try {
            current = ReadWriteFile.getLoginData(currentUsername);
        } catch (IOException ex) {
            Logger.getLogger(ViewTherapistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String currentFirst = current.get(2);
        String currentLast = current.get(3);
        String currentUser = current.get(0);
        String currentPass = current.get(1);
        
        String newFirst = firstname.getText();
        String newLast = surname.getText();
        String newUser = username.getText();
        String newPass = password.getText();
        
        ReadWriteFile.editLoginFile(
                "Username: " + currentUser + System.getProperty("line.separator") + //current info
                "Password: " + currentPass  + System.getProperty("line.separator") + //current info
                "Firstname: " + currentFirst + System.getProperty("line.separator") + //current info
                "Surname: " + currentLast, //current info
                
                "Username: " + newUser + System.getProperty("line.separator") + //new info
                "Password: " + newPass + System.getProperty("line.separator") + //new info
                "Firstname: " + newFirst + System.getProperty("line.separator") + //new info
                "Surname: " + newLast); //new info
        
        SwitchWindow.switchWindow((Stage)save.getScene().getWindow(), new Mainscreen());
    }

    @FXML
    private void discard(ActionEvent event) {
            SwitchWindow.switchWindow((Stage)discard.getScene().getWindow(), new Mainscreen());
    }

    @FXML
    private void search(ActionEvent event){
        
        String currentUsername = currentUser.getText();
        
        try {
            if (!ReadWriteFile.getUsernames().contains(currentUsername)){
                return;
            }
        } catch (IOException ex) {
            Logger.getLogger(ViewTherapistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ArrayList<String> current = null;
        try {
            current = ReadWriteFile.getLoginData(currentUsername);
        } catch (IOException ex) {
            Logger.getLogger(ViewTherapistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        firstname.setText(current.get(2));
        surname.setText(current.get(3));
        username.setText(current.get(0));
        password.setText(current.get(1));
    }
    
}
