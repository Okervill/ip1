/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integratedproject1;

import java.io.IOException;
import java.util.Date;

/**
 *
 * @author patrick
 */
public class Appointment {
    
    private int appointmentNumber;
    private int patientNumber;
    private String therapist;
    private String date;//Date date;
    private String service;
    private int cost = 4;
    private String status;
    private String time = "13:05";
    
    public Appointment(int p, String t, String d, String s) throws IOException{
        
        this.patientNumber = p;
        this.service = s;
        this.therapist = t;
        this.date = d;
        status = "pending";
        this.appointmentNumber = ReadWriteFile.countAppointments() + 1;
        
        ReadWriteFile.newAppointment(appointmentNumber, p, t, d, time, service, cost, status);
    }
    
}
