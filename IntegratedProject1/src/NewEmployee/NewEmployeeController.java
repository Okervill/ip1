/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NewEmployee;

import integratedproject1.Therapist;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewEmployeeController implements Initializable {

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
        Stage stage = (Stage) discard.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void save(ActionEvent event) throws IOException, SQLException {
        String f = firstname.getText();
        String s = surname.getText();
        String p = password.getText();
        String m = type.getSelectionModel().getSelectedItem();

        if (f.length() < 1 || s.length() < 1 || p.length() < 1 || m.length() < 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Enter details!");
            alert.setContentText("You must enter details in all boxes");
            alert.showAndWait();
            return;
        }

        Therapist t = new Therapist(f, s, p, m);

        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
    }

}
