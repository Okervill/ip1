/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NewService;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Patrick
 */
public class NewService extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/NewService/NewService.fxml"));
        Parent root = (Parent) loader.load();
        Stage secondStage = new Stage();
        secondStage.setScene(new Scene(new HBox(root)));
        
        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.showAndWait();
    }
}