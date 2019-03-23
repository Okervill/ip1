/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NewService;

import SQL.SQLHandler;
import integratedproject1.Services;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashSet;
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

/**
 * FXML Controller class
 *
 * @author choco
 */
public class NewServiceController implements Initializable {

    @FXML
    private TextField serviceName;
    @FXML
    private ChoiceBox<String> serviceActive;
    @FXML
    private Button save;
    @FXML
    private Button discard;

    SQLHandler sql = new SQLHandler();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serviceActive.setItems(FXCollections.observableArrayList("Active", "Inactive"));

    }

    @FXML
    private void save(ActionEvent event) throws SQLException {

        String name = serviceName.getText();
        String active = serviceActive.getSelectionModel().getSelectedItem();

        if (name.isEmpty() || active.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Missing details");
            alert.setContentText("You must enter a service name and whether or not it should be active immediately");
            alert.showAndWait();
        }
        if (sql.getAllServices().contains(name)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Service Exists");
            alert.setContentText("Service already exists");
            alert.showAndWait();
        }
        Services newService = new Services(name, active);

        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void discard(ActionEvent event) {
        Stage stage = (Stage) discard.getScene().getWindow();
        stage.close();
    }

}
