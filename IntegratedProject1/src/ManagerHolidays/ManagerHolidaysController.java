/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagerHolidays;

import EditHoliday.EditHoliday;
import ManageHoliday.ManageHolidayController;
import ManagerEditHoliday.ManagerEditHolidays;
import SQL.SQLHandler;
import integratedproject1.SwitchWindow;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author patrick
 */
public class ManagerHolidaysController implements Initializable {
    
    SQLHandler sql = new SQLHandler();
    
    @FXML
    private ListView<String> displayHolidays;
    @FXML
    private Button Close;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            try {
                displayHolidays();
            } catch (SQLException ex) {
                Logger.getLogger(ManagerHolidaysController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    private void editHoliday(MouseEvent event) throws SQLException {
        
        if (displayHolidays.getSelectionModel().isEmpty()) return;

        String selection = displayHolidays.getSelectionModel().getSelectedItem();
        String id = selection.substring(selection.indexOf(": ") + 2, selection.indexOf(" E"));
        SwitchWindow.switchWindow((Stage) Close.getScene().getWindow(), new ManagerEditHolidays(id));
        displayHolidays();
    }

    public void displayHolidays() throws SQLException {
        ArrayList<String> allHolidays = sql.getAllHolidays();

        ObservableList<String> appointments = FXCollections.observableArrayList();

        for (int i = 0; i < allHolidays.size(); i++) {
            appointments.add(allHolidays.get(i));
        }

        displayHolidays.setItems(appointments);

    }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) Close.getScene().getWindow();
        stage.close();
    }
    
}
