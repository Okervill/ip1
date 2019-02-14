/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integratedproject1;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author choco
 */
public class Therapist {
    
    private String firstname;
    private String surname;
    private String username;
    private String password;
    private boolean manager = false;
    
    public Therapist(String f, String s, String p){
        this.firstname = f;
        this.surname = s;
        this.password = p;
        this.username = surname + firstname.charAt(0);
        try {
            ReadWriteFile.updateLoginFile(firstname, surname, username, password, manager);
        } catch (IOException ex) {
            Logger.getLogger(Therapist.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Therapist(String f, String s, String u, String p, boolean m){
        this.firstname = f;
        this.surname = s;
        this.password = p;
        this.username = u;
        this.manager = m;
        try {
            ReadWriteFile.updateLoginFile(firstname, surname, username, password, manager);
        } catch (IOException ex) {
            Logger.getLogger(Therapist.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getFirstname(){ return firstname; }
    public String getSurname(){ return surname; }
    public String getFullname(){ return firstname + " " + surname; }
    
    public static void changePassword(String username, String newPassword){
        try {
            ReadWriteFile.editLoginFile("Password: " + (String) ReadWriteFile.getLoginData(username).get(1), "Password: " + newPassword);
        } catch (IOException ex) {
            Logger.getLogger(Therapist.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
    
}
