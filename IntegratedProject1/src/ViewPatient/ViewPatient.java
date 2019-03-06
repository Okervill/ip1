/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewPatient;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewPatient extends Application {
    
    private final ArrayList<String> patientData;
    
    public ViewPatient(ArrayList<String> patientData) {
        this.patientData = patientData;
    }
    
    @Override
    public void start(Stage stage) throws Exception {    
    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewPatient/ViewPatient.fxml"));
        Parent root = (Parent)loader.load();
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("View Patient");
        
        ViewPatientController controller = loader.getController();
        controller.setData(patientData);
        
        stage.show();        
        stage.centerOnScreen();
        
    }
    
}