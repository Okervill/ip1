/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagerEditHoliday;

import SQL.SQLHandler;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author patrick
 */
public class ManagerEditHolidaysController implements Initializable {

    ArrayList<String> holidayDetails;
    String holidayId;
    SQLHandler sql = new SQLHandler();

    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Button Save;
    @FXML
    private Button Delete;
    @FXML
    private Button Cancel;
    @FXML
    private ChoiceBox<String> approved;
    @FXML
    private Label employeeName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void save(ActionEvent event) {

        LocalDate start = startDate.getValue();
        LocalDate end = endDate.getValue();

        if (end.isBefore(start)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Date error");
            alert.setContentText("End date is before start");
            alert.showAndWait();
            return;
        }

        if (start.isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Date error");
            alert.setContentText("Date is before today");
            alert.showAndWait();
            return;
        }

        try {
            sql.updateHoliday(holidayId, holidayDetails.get(1), start, end, approved.getSelectionModel().getSelectedItem(), "1");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText("Holiday Updated");
            alert.showAndWait();
        } catch (SQLException ex) {
            Logger.getLogger(ManagerEditHolidaysController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Please try again");
            alert.showAndWait();
            return;
        }

        Stage stage = (Stage) Save.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void delete(ActionEvent event) throws SQLException {
        sql.deleteRecord("holiday", "id", holidayId);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Successfully Deleted");
        alert.showAndWait();

        Stage stage = (Stage) Delete.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) Cancel.getScene().getWindow();
        stage.close();
    }

    public void setData(String holiday) throws SQLException {
        holidayId = holiday;
        holidayDetails = sql.search("holiday", "id", holidayId);
        employeeName.setText(sql.search("login", "username", holidayDetails.get(1)).get(2) + " " + sql.search("login", "username", holidayDetails.get(1)).get(3));
        startDate.setValue(LocalDate.parse(holidayDetails.get(2)));
        endDate.setValue(LocalDate.parse(holidayDetails.get(3)));

        approved.setItems(FXCollections.observableArrayList("Accepted", "Declined", "Pending"));
        approved.getSelectionModel().select(holidayDetails.get(4));
    }

}
