/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EditEmployee;

import SQL.SQLHandler;
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

public class EditEmployeeController implements Initializable {

    @FXML
    private TextField firstname;
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
    @FXML
    private TextField password;
    @FXML
    private ChoiceBox<String> active;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manager.setItems(FXCollections.observableArrayList("therapist", "receptionist", "manager"));
        active.setItems(FXCollections.observableArrayList("true", "false"));
    }

    @FXML
    private void save(ActionEvent event) throws IOException, SQLException {

        String currentUsername = currentUser.getText();
        if (currentUsername.length() < 1) {
            return;
        }
        if (!password.getText().isEmpty()) {
            sql.updateLoginPassword(currentUsername, password.getText());
        }

        ArrayList<String> current = null;
        try {
            current = sql.search("login", "username", currentUsername);
        } catch (SQLException ex) {
            Logger.getLogger(EditEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        String newFirst = firstname.getText();
        String newLast = surname.getText();
        String newUserType = manager.getSelectionModel().getSelectedItem();
        String newActive = active.getSelectionModel().getSelectedItem();

        sql.updateLogin(currentUsername, newFirst, newLast, newUserType, newActive);

        Stage stage = (Stage) discard.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void discard(ActionEvent event) {
        Stage stage = (Stage) discard.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void search(ActionEvent event) {

        String currentUsername = currentUser.getText();

        try {
            if (!sql.checkUserExists(currentUsername)) {
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EditEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<String> current = null;
        try {
            current = sql.search("login", "username", currentUsername);
        } catch (SQLException ex) {
            Logger.getLogger(EditEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        firstname.setText(current.get(2));
        surname.setText(current.get(3));
        if (current.get(4).equalsIgnoreCase("manager")) {
            manager.getSelectionModel().select("manager");
        } else if (current.get(4).equalsIgnoreCase("receptionist")) {
            manager.getSelectionModel().select("receptionist");
        } else if (current.get(4).equalsIgnoreCase("therapist")) {
            manager.getSelectionModel().select("therapist");
        }

        if (current.get(5).equalsIgnoreCase("false")) {
            active.getSelectionModel().select("false");
        } else if (current.get(5).equalsIgnoreCase("true")) {
            active.getSelectionModel().select("true");
        }

    }
}
