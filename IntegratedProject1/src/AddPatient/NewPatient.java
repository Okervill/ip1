/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddPatient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author patrick
 */
public class NewPatient extends Application {
    
    public NewPatient() {
        
    }
    
    @Override
    public void start(Stage stage) throws Exception {    
    
        Parent root = FXMLLoader.load(getClass().getResource("/AddPatient/NewPatient.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("New Patient");
        stage.show();        
        stage.centerOnScreen();
    }
    
}
