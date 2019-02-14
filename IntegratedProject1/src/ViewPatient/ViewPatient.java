/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewPatient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewPatient extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {    
    
        Parent root = FXMLLoader.load(getClass().getResource("/ViewPatient/ViewPatient.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("View Patient");
        stage.show();
        
    }
    
}
