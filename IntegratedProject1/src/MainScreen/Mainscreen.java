/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainScreen;

import integratedproject1.ReadWriteFile;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Mainscreen extends Application {

    String userType;
    String username;

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScreen/Mainscreen.fxml"));
        Parent root = (Parent) loader.load();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Home");

        MainscreenController controller = loader.getController();
        controller.setData(username, userType);

        stage.show();
        stage.centerOnScreen();

    }

    public Mainscreen() {
    }
    
    public Mainscreen(String u, String t){
        username = u;
        userType = t;
    }
}
