/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integratedproject1;

import SQL.SQLHandler;
import java.sql.SQLException;
import java.time.LocalDate;


public class Patient {
    private final String firstname;
    private final String surname;
    private final String email;
    private final String mobile;
    private final LocalDate dob;
    private final String gender;
    private final String postcode;
    private final String patientNo;
    
    SQLHandler sql = new SQLHandler();
    
    
    public Patient(String f, String s, String e, String m, LocalDate d, String g, String p) throws SQLException {
        this.firstname = f;
        this.surname = s;
        this.email = e;
        this.mobile = m;
        this.dob = d;
        this.gender = g;
        this.postcode = p;
        
        patientNo = String.valueOf(sql.countRecords("patient") + 1);
        sql.addToPatient(firstname, surname, email, mobile, dob, gender, postcode, patientNo);
        
    }
    
    
}
