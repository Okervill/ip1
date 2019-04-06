/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManageHoliday;

import EditHoliday.EditHoliday;
import NewHoliday.NewHoliday;
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
public class ManageHolidayController implements Initializable {

    SQLHandler sql = new SQLHandler();

    String username;

    @FXML
    private Button newhol;
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
            displayHolidays();
        });
            
    }

    @FXML

    private void newHoliday(ActionEvent event) {
        SwitchWindow.switchWindow((Stage) newhol.getScene().getWindow(), new NewHoliday(username));
        displayHolidays();
    }

    public void setData(String user) {
        username = user;
    }

    @FXML
    private void editHoliday(MouseEvent event) {
        
        if (displayHolidays.getSelectionModel().isEmpty()) return;

        String selection = displayHolidays.getSelectionModel().getSelectedItem();
        String id = selection.substring(selection.indexOf(" ") + 1, selection.indexOf(" S"));
        
        SwitchWindow.switchWindow((Stage) newhol.getScene().getWindow(), new EditHoliday(id));
        displayHolidays();
    }

    public void displayHolidays() {
        ArrayList<String> allHolidays = new ArrayList<>();
        try {
            allHolidays = sql.getTherapistHoliday(username);
        } catch (SQLException ex) {
            Logger.getLogger(ManageHolidayController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
