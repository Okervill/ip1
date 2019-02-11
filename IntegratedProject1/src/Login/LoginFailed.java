/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import integratedproject1.ReadWriteFile;
import integratedproject1.ReadWriteFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginFailed extends Application {
    

    public void start(Stage stage) throws Exception {
        ReadWriteFile.createFile();
        ReadWriteFile.updateFile();
        
        Parent root = FXMLLoader.load(getClass().getResource("LoginFailed.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("LoginFailed");
        stage.show();
        
        
    }
}
