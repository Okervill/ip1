/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integratedproject1;

import java.util.Date;


public class Patient {
    private String firstname;
    private String surname;
    private String email;
    private String mobile;
    private Date dob;
    private String gender;
    
    public Patient(String f, String s, String e, String m, Date d, String g){
        this.firstname = f;
        this.surname = s;
        this.email = e;
        this.mobile = m;
        this.dob = d;
        this.gender = g;
    }
    
}
