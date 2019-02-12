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
            ReadWriteFile.updateFile(firstname, surname, username, password, manager);
        } catch (IOException ex) {
            Logger.getLogger(Therapist.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Therapist(String f, String s, String p, String u, boolean m){
        this.firstname = f;
        this.surname = s;
        this.password = p;
        this.username = u;
        this.manager = m;
        try {
            ReadWriteFile.updateFile(firstname, surname, username, password, manager);
        } catch (IOException ex) {
            Logger.getLogger(Therapist.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getFirstname(){ return firstname; }
    public String getSurname(){ return surname; }
    public String getFullname(){ return firstname + " " + surname; }
    
    
}
