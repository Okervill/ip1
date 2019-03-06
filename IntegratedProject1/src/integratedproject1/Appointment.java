/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integratedproject1;

import SQL.SQLHandler;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author patrick
 */
public class Appointment {
    
    SQLHandler sql = new SQLHandler();
    
    public Appointment(String patientNumber, String therapist, LocalDate date, LocalTime time, String service, String cost) throws SQLException{
        
        String status = "pending";
        String appointmentNumber = String.valueOf(sql.countRecords("appointment") + 1);
        
        sql.addToAppointment(appointmentNumber, patientNumber, therapist, date, time, service, cost, status);
    }
    
}
