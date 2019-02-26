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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Patient {
    private String firstname;
    private String surname;
    private String email;
    private String mobile;
    private LocalDate dob;
    private String gender;
    private String postcode;
    private String patientNo;
    
    SQLHandler sql = new SQLHandler();
    
    
    public Patient(String f, String s, String e, String m, LocalDate d, String g, String p) throws SQLException {
        this.firstname = f;
        this.surname = s;
        this.email = e;
        this.mobile = m;
        this.dob = d;
        this.gender = g;
        this.postcode = p;
        
        patientNo = String.valueOf(Integer.valueOf(sql.countRecords("patient")) + 1);//ReadWriteFile.countPatients() + 1;
        sql.addToPatient(firstname, surname, email, mobile, dob, gender, postcode, patientNo);
        
        //ReadWriteFile.updatePatientFile(firstname, surname, email, mobile, dob, gender, postcode, patientNo);
    }
    
    
}
