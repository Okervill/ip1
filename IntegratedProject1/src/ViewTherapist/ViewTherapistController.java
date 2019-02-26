/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewTherapist;

import MainScreen.Mainscreen;
import SQL.SQLHandler;
import integratedproject1.SwitchWindow;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ViewTherapistController implements Initializable {

    @FXML
    private TextField firstname;
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
    @FXML
    private ChoiceBox<String> manager;

    
    SQLHandler sql = new SQLHandler();
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manager.setItems(FXCollections.observableArrayList("therapist", "receptionist", "manager"));
    }

    @FXML
    private void save(ActionEvent event) throws IOException, SQLException {

        String currentUsername = currentUser.getText();

        ArrayList<String> current = null;
        try {
            current = sql.search("login", "username", currentUsername);//ReadWriteFile.getLoginData(currentUsername);
        } catch (SQLException ex) {
            Logger.getLogger(ViewTherapistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
                

        String newFirst = firstname.getText();
        String newLast = surname.getText();
        String newPass = password.getText();
        String newUserType = manager.getSelectionModel().getSelectedItem();
        
        if (password.getText().length() < 1){
            newPass = current.get(1);
        }
        
        sql.updateLogin(currentUsername, newPass, newFirst, newLast, newUserType);

//        ReadWriteFile.editLoginFile(
//                "src/Login/LoginData.txt",
//                "Username: " + currentUser + System.getProperty("line.separator")
//                + //current info
//                "Password: " + currentPass + System.getProperty("line.separator")
//                + //current info
//                "Firstname: " + currentFirst + System.getProperty("line.separator")
//                + //current info
//                "Surname: " + currentLast + System.getProperty("line.separator")
//                + //current info
//                "Manager: " + currentManager,
//                "Username: " + currentUser + System.getProperty("line.separator")
//                + //new info
//                "Password: " + newPass + System.getProperty("line.separator")
//                + //new info
//                "Firstname: " + newFirst + System.getProperty("line.separator")
//                + //new info
//                "Surname: " + newLast + System.getProperty("line.separator")
//                + //new info
//                "Manager: " + newManager); //new info

        SwitchWindow.switchWindow((Stage) save.getScene().getWindow(), new Mainscreen());
    }

    @FXML
    private void discard(ActionEvent event) {
        SwitchWindow.switchWindow((Stage) discard.getScene().getWindow(), new Mainscreen());
    }

    @FXML
    private void search(ActionEvent event) {

        String currentUsername = currentUser.getText();
        
        try {
            if (!sql.checkTherapistExists(currentUsername)) {
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ViewTherapistController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<String> current = null;
        try {
            current = sql.search("login", "username", currentUsername);
        } catch (SQLException ex) {
            Logger.getLogger(ViewTherapistController.class.getName()).log(Level.SEVERE, null, ex);
        }

        firstname.setText(current.get(2));
        surname.setText(current.get(3));
        if (current.get(4).equalsIgnoreCase("manager")) {
            manager.getSelectionModel().select("manager");
        }
        if (current.get(4).equalsIgnoreCase("receptionist")) {
            manager.getSelectionModel().select("receptionist");
        }
        if (current.get(4).equalsIgnoreCase("therapist")) {
            manager.getSelectionModel().select("therapist");
        }

    }

}
