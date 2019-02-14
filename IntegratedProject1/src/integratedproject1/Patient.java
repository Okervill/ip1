/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integratedproject1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Patient {
    private String forename;
    private String surname;
    private String email;
    private String mobile;
    private String dob;
    private String gender;
    private String postcode;
    private int patientNo;
    
    public Patient(String f, String s, String e, String m, String d, String g, String p) throws IOException{
        this.forename = f;
        this.surname = s;
        this.email = e;
        this.mobile = m;
        this.dob = d;
        this.gender = g;
        this.postcode = p;
        
        patientNo = ReadWriteFile.countPatients() + 1;
        
        try {
            ReadWriteFile.updatePatientFile(forename, surname, email, mobile, dob, gender, postcode, patientNo);
        } catch (IOException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getFirstname(){ return forename; }
    public String getSurname(){ return surname; }
    public String getEmail(){ return email; }
    public String getMobile(){ return mobile; }
    public String getDOB(){ return dob; }
    public String getGender(){ return gender; }
    public String getPostcode() { return postcode; }
    public int getPatientNo() { return patientNo; }
    
    public ArrayList getData(){
        ArrayList<String> info = new ArrayList<>();
        
        info.add(forename);
        info.add(surname);
        info.add(email);
        info.add(mobile);
        info.add(dob);
        info.add(gender);
        info.add(postcode);
        
        return info;
    }
    
    
}
