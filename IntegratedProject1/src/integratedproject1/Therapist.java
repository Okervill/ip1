/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integratedproject1;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Therapist {
    
    private String firstname;
    private String surname;
    private String username;
    private String password;
    private String manager;
    
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
    
    public Therapist(String f, String s, String p, String m) throws IOException{
        this.firstname = f;
        this.surname = s;
        this.password = p;
        this.manager = m;
        
        this.username = s + f.charAt(0);
        
        int count = 0;
        
        for(int i = 0; i < ReadWriteFile.getUsernames().size(); i++){
            String str = (String) ReadWriteFile.getUsernames().get(i);
            if (str.contains(username)) count++;
        }
        
        if (ReadWriteFile.getUsernames().contains(username)){
            username = username + count;
        }
        
        newTherapist(firstname, surname, username, password, manager);
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
    
    public void newTherapist(String f, String s, String u, String p, String m) throws IOException{
        ReadWriteFile.updateLoginFile(f, s, u, p, m);
    }
    
    
}
