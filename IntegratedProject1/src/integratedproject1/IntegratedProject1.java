/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integratedproject1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
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
        File appointments = new File("src/integratedproject1/appointments.txt");
        //if login file doesnt exist create it with some test data
        if (!loginData.exists()) {
            ReadWriteFile.createFile(loginData, 5);
            Therapist manager = new Therapist("admin", "admin", "admin", "true");
            Therapist t1 = new Therapist("Tony", "Stark", "ironman", "false");
            Therapist t2 = new Therapist("Bruce", "Wayne", "batman", "false");
        }
        //if patient file doesnt exist create it with some test data
        if (!patientData.exists()) {
            ReadWriteFile.createFile(patientData, 8);
            Patient p1 = new Patient("John", "Doe", "johndoe@gmail.com", "07987654321", "01/01/1971", "Male", "G1 0AA");
            Patient p2 = new Patient("Jane", "Doe", "janedoe@gmail.com", "07123456789", "02/02/1972", "Female", "G1 0AA");

        }
        //if appointment file doesn exist create it with some test data
        if (!appointments.exists()) {
            ReadWriteFile.createFile(appointments, 8);
            //dates
            LocalDate d1 = LocalDate.of(2019, Month.FEBRUARY, 2);
            LocalDate d2 = LocalDate.of(2019, Month.MARCH, 3);
            //times
            LocalTime t1 = LocalTime.of(13, 5);
            LocalTime t2 = LocalTime.of(12, 5);
            
            Appointment a1 = new Appointment(1, "StarkT", d1, "Sports Massage", t1);
            Appointment a2 = new Appointment(1, "WayneB", d1, "Acupuncture", t2);
            Appointment a3 = new Appointment(2, "WayneB", d2, "Physiotherapy", t1);
            Appointment a4 = new Appointment(2, "StarkT", d2, "Sports Massage", t2);
        }
        launch(args);
    }

}
