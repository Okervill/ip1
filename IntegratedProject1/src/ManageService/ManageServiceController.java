/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManageService;

import MainScreen.MainscreenController;
import SQL.SQLHandler;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 * @author Patrick
 */
public class ManageServiceController implements Initializable {

    @FXML
    private ChoiceBox<String> selectService;
    @FXML
    private TextField serviceName;
    @FXML
    private ChoiceBox<String> selectActive;
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
        selectActive.setItems(FXCollections.observableArrayList("Active", "Inactive"));
        ArrayList<String> allServices = new ArrayList<>();

        try {
            allServices = sql.getAllServices();
        } catch (SQLException ex) {
            Logger.getLogger(ManageServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ObservableList<String> services = FXCollections.observableArrayList();

        for (int i = 0; i < allServices.size(); i++) {
            services.add(allServices.get(i));
        }
        selectService.setItems(services);
        selectService.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            String selectedService = newValue;
            try {
                displayDetails(selectedService);
            } catch (SQLException ex) {
                Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    @FXML
    private void save(ActionEvent event) throws SQLException {
        String selected = selectService.getSelectionModel().getSelectedItem();
        String name = serviceName.getText();
        String active = selectActive.getSelectionModel().getSelectedItem();

        if (name.isEmpty() || active.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Missing details");
            alert.setContentText("You cannot leave the service name empty");
            alert.showAndWait();
            return;
        }

        ArrayList<String> allServices;

        try {
            allServices = sql.getAllServices();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to save");
            alert.setContentText("Unable to save to database, please try again later");
            alert.showAndWait();
            return;
        }
        if (!name.equals(selected) && sql.getAllServices().contains(name)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Service name");
            alert.setContentText("A service with that name already exists");
            alert.showAndWait();
            return;
        }

        try {
            sql.updateService(sql.search("service", "name", selectService.getSelectionModel().getSelectedItem()).get(0), name, active);
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to save");
            alert.setContentText("Unable to save to database, please try again later");
            alert.showAndWait();
            return;
        }

        Stage stage = (Stage) discard.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void discard(ActionEvent event) {
        Stage stage = (Stage) discard.getScene().getWindow();
        stage.close();
    }

    public void displayDetails(String service) throws SQLException {
        ArrayList<String> serviceInfo = sql.search("service", "name", service);
        serviceName.setText(serviceInfo.get(1));
        selectActive.getSelectionModel().select(serviceInfo.get(2));
    }

}
