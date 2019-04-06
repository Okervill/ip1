/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EditHoliday;

import SQL.SQLHandler;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author patrick
 */
public class EditHolidayController implements Initializable {
    
    SQLHandler sql = new SQLHandler();

    String holidayId;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Label approved;
    @FXML
    private Button Save;
    @FXML
    private Button Delete;
    @FXML
    private Button Cancel;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setData(String id) throws SQLException {
        holidayId = id;
        
        ArrayList<String> holidayDetails = sql.search("holiday", "id", id);
        
        startDate.setValue(LocalDate.parse(holidayDetails.get(2)));
        endDate.setValue(LocalDate.parse(holidayDetails.get(3)));
        
        approved.setText(holidayDetails.get(4));
    }

    @FXML
    private void save(ActionEvent event) {
    }

    @FXML
    private void delete(ActionEvent event) throws SQLException {
        sql.deleteRecord("holiday", "id", holidayId);
        Stage stage = (Stage) Cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) Cancel.getScene().getWindow();
        stage.close();
    }
    
}
