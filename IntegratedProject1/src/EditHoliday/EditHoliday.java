/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EditHoliday;

import ManageHoliday.ManageHolidayController;
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
public class EditHoliday extends Application {
    
    String holidayId;
    
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditHoliday/EditHoliday.fxml"));
        Parent root = (Parent) loader.load();
        Stage secondStage = new Stage();
        secondStage.setScene(new Scene(new HBox(root)));
        
        EditHolidayController controller = loader.getController();
        controller.setData(holidayId);
        
        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.showAndWait();
    }
    
    public EditHoliday(String id){
        holidayId = id;
    }
}