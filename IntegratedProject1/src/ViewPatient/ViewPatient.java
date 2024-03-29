/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewPatient;

import EditAppointment.EditAppointmentController;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewPatient extends Application {
    
    private final ArrayList<String> patientData;
    
    public ViewPatient(ArrayList<String> patientData) {
        this.patientData = patientData;
    }
    
    @Override
    public void start(Stage stage) throws Exception {    
    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewPatient/ViewPatient.fxml"));
        Parent root = (Parent) loader.load();
        Stage secondStage = new Stage();
        secondStage.setScene(new Scene(new HBox(root)));
        
        ViewPatientController controller = loader.getController();
        controller.setData(patientData);

        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.showAndWait();
        
    }
    
}