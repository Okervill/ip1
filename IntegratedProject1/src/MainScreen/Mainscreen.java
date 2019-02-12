/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainScreen;

import integratedproject1.ReadWriteFile;
import integratedproject1.ReadWriteFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Mainscreen extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {    
    
        Parent root = FXMLLoader.load(getClass().getResource("/MainScreen/Mainscreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Mainscreen");
        stage.show();
        
    }
    
}
