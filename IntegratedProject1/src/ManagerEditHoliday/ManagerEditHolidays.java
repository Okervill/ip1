/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagerEditHoliday;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author patrick
 */
public class ManagerEditHolidays extends Application {

    String holidayDetails;
    
    @Override
    public void start(Stage stage) throws Exception {    
    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ManagerEditHoliday/ManagerEditHolidays.fxml"));
        Parent root = (Parent) loader.load();
        Stage secondStage = new Stage();
        secondStage.setScene(new Scene(new HBox(root)));
        
        ManagerEditHolidaysController controller = loader.getController();
        controller.setData(holidayDetails);

        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.showAndWait();
        
    }
    public ManagerEditHolidays(String holidayId) {
        holidayDetails = holidayId;
    }
}