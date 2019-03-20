/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EditAppointment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditAppointment extends Application {
    
    String appno;

    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditAppointment/EditAppointment.fxml"));
        Parent root = (Parent) loader.load();
        Stage secondStage = new Stage();
        secondStage.setScene(new Scene(new HBox(root)));
        
        
        EditAppointmentController controller = loader.getController();
        controller.setData(appno);

        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.showAndWait();
    }

    public EditAppointment(String a) {
        this.appno = a;
    }
}
