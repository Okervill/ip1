/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NewAppointment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewAppointment extends Application {
    
    String patientNumber;

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/NewAppointment/NewAppointment.fxml"));
        Parent root = (Parent) loader.load();
        Stage secondStage = new Stage();
        secondStage.setScene(new Scene(new HBox(root)));
        
        NewAppointmentController controller = loader.getController();
        controller.setData(patientNumber);

        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.showAndWait();
    }
    
    public NewAppointment(String patientNo){
        this.patientNumber = patientNo;
    }
}
