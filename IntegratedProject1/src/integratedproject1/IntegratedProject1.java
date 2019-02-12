/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integratedproject1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class IntegratedProject1 extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/Login/Login.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();

    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        File loginData = new File("src/Login/LoginData.txt");
        File patientData = new File("src/integratedproject1/PatientFile.txt");
        
        if (!loginData.exists()) { //Creates a file with test input
            ReadWriteFile.createLoginFile();
            Therapist manager = new Therapist("admin", "admin", "admin", "admin", true);
        }
        if (!patientData.exists()){ //Creates a file with test input
            ReadWriteFile.createPatientFile();
            Patient test1 = new Patient("John", "Doe", "johndoe@gmail.com", "07000000000", "01/01/1970", "Male", "G1 0AA");
        }
        launch(args);
    }

}
