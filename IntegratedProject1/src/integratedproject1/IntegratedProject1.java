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
            Therapist manager = new Therapist("admin", "admin", "admin", "true");
            Therapist t1 = new Therapist("Tony", "Stark", "ironman", "false");
            Therapist t2 = new Therapist("Bruce", "Wayne", "batman", "false");
        }
        if (!patientData.exists()){ //Creates a file with test input
            ReadWriteFile.createPatientFile();
            Patient p1 = new Patient("John", "Doe", "johndoe@gmail.com", "07987654321", "01/01/1971", "Male", "G1 0AA");
            Patient p2 = new Patient("Jane", "Doe", "janedoe@gmail.com", "07123456789", "02/02/1972", "Female", "G1 0AA");
        }
        System.out.println(ReadWriteFile.getPatientData("John", "Doe", "G1 0AA"));
        launch(args);
    }

}
