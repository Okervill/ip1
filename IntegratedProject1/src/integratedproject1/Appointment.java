/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integratedproject1;

import SQL.SQLHandler;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author patrick
 */
public class Appointment {
    
    private String appointmentNumber;
    private String patientNumber;
    private String therapist;
    private LocalDate date;//Date date;
    private String service;
    private String cost = "4";
    private String status;
    private LocalTime time;
    
    SQLHandler sql = new SQLHandler();
    
    public Appointment(String patientNumber, String therapist, LocalDate date, String service, LocalTime time) throws IOException, SQLException{
        
        this.patientNumber = patientNumber;
        this.service = service;
        this.therapist = therapist;
        this.date = date;
        this.time = time;
        status = "pending";
        this.appointmentNumber = String.valueOf(Integer.valueOf(sql.countRecords("appointment")) + 1);//ReadWriteFile.countAppointments() + 1;
        
        sql.addToAppointment(appointmentNumber, patientNumber, therapist, date, time, service, cost, status);
        //ReadWriteFile.newAppointment(appointmentNumber, p, t, d, time, service, cost, status);
    }
    
}
