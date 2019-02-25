/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewTherapist;

import MainScreen.Mainscreen;
import integratedproject1.ReadWriteFile;
import integratedproject1.SwitchWindow;
import java.io.IOException;
import java.net.URL;
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

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manager.setItems(FXCollections.observableArrayList("true", "false"));
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
        String currentManager = current.get(4);

        String newFirst = firstname.getText();
        String newLast = surname.getText();
        String newPass = password.getText();
        String newManager = manager.getSelectionModel().getSelectedItem();

        if (password.getText().length() < 1) {
            newPass = currentPass;
        }

        ReadWriteFile.editLoginFile(
                "src/Login/LoginData.txt",
                "Username: " + currentUser + System.getProperty("line.separator")
                + //current info
                "Password: " + currentPass + System.getProperty("line.separator")
                + //current info
                "Firstname: " + currentFirst + System.getProperty("line.separator")
                + //current info
                "Surname: " + currentLast + System.getProperty("line.separator")
                + //current info
                "Manager: " + currentManager,
                "Username: " + currentUser + System.getProperty("line.separator")
                + //new info
                "Password: " + newPass + System.getProperty("line.separator")
                + //new info
                "Firstname: " + newFirst + System.getProperty("line.separator")
                + //new info
                "Surname: " + newLast + System.getProperty("line.separator")
                + //new info
                "Manager: " + newManager); //new info

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
            if (!ReadWriteFile.getUsernames("all").contains(currentUsername)) {
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
        if (current.get(4).equalsIgnoreCase("true")) {
            manager.getSelectionModel().select("true");
        }
        if (current.get(4).equalsIgnoreCase("false")) {
            manager.getSelectionModel().select("false");
        }

    }

}
