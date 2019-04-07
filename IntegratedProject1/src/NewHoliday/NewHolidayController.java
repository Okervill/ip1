/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NewHoliday;

import SQL.SQLHandler;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author patrick
 */
public class NewHolidayController implements Initializable {

    String username;

    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Button Save;
    @FXML
    private Button cancel;

    SQLHandler sql = new SQLHandler();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void save(ActionEvent event) throws SQLException, SQLException {
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

        int holidayCount = sql.countRecords("holiday") + 1;

        if (sql.checkHolidayExists(String.valueOf(holidayCount))) {
            while (sql.search("holiday", "id", String.valueOf(holidayCount)).size() >= 4) {
                holidayCount++;
            }
        }

        sql.addToHoliday(String.valueOf(holidayCount), username, start, end, "Pending", "0");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText("Holiday submitted");
        alert.setContentText("Holiday submitted for approval");
        alert.showAndWait();

        Stage stage = (Stage) Save.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void Cancel(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    void setData(String user) {
        username = user;
    }

    @FXML
    private void pickDate(ActionEvent event) {
        if (startDate.getValue().isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Date error");
            alert.setContentText("Date is before today");
            alert.showAndWait();
            startDate.setValue(LocalDate.now());
            return;
        }
        endDate.setValue(startDate.getValue());
    }

}
