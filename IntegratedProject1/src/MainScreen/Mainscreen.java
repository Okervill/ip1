/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainScreen;

import ViewPatient.ViewPatientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Mainscreen extends Application {

    boolean manager = false;

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScreen/Mainscreen.fxml"));
        Parent root = (Parent) loader.load();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Home");

        MainscreenController controller = loader.getController();
        controller.setData(manager);

        stage.show();

    }

    public Mainscreen(boolean m) {
        manager = m;
    }

    public Mainscreen() {

    }
}
