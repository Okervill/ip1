/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integratedproject1;

import SQL.SQLHandler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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

    public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {
        /*
        
        TEST DATA
        
        //Appointment dates
        LocalDate d1 = LocalDate.of(2019, Month.FEBRUARY, 2);
        LocalDate d2 = LocalDate.of(2019, Month.MARCH, 3);
        
        //Appointment times
        LocalTime time1 = LocalTime.of(13, 5);
        LocalTime time2 = LocalTime.of(12, 5);

        //Patient DoBs
        LocalDate dob1 = LocalDate.of(1995, Month.DECEMBER, 29);
        LocalDate dob2 = LocalDate.of(1995, Month.JULY, 24);

        //Patient test data
        Patient p1 = new Patient("John", "Doe", "johndoe@gmail.com", "07987654321", dob1, "Male", "G1 0AA");
        Patient p2 = new Patient("Jane", "Doe", "janedoe@gmail.com", "07123456789", dob2, "Female", "G1 0AA");

        //Appointment test data
        Appointment a1 = new Appointment("1", "StarkT", d1, "Sports Massage", time1);
        Appointment a2 = new Appointment("1", "WayneB", d1, "Acupuncture", time2);
        Appointment a3 = new Appointment("2", "WayneB", d2, "Physiotherapy", time1);
        Appointment a4 = new Appointment("2", "StarkT", d2, "Sports Massage", time2);
        
        //Therapist test data
        Therapist t1 = new Therapist("admin", "admin", "admin", "manager");
        Therapist t2 = new Therapist("Tony", "Stark", "ironman", "therapist");
        Therapist t3 = new Therapist("Bruce", "Wayne", "batman", "therapist");
        Therapist t4 = new Therapist("Stan", "Lee", "marvel", "receptionist");


         */
        launch(args);
    }

}
