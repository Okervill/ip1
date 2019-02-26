/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integratedproject1;

import SQL.SQLHandler;
import java.io.IOException;
import java.sql.SQLException;

public class Therapist {

    private String firstname;
    private String surname;
    private String username;
    private String password;
    private String userType;
    
    SQLHandler sql = new SQLHandler();

    public Therapist(String f, String s, String p, String t) throws SQLException {
        this.firstname = f;
        this.surname = s;
        this.password = p;
        this.userType = t;

        this.username = s + f.charAt(0);

        int count = 0;

        for (int i = 0; i < sql.getAllUsernames("all").size(); i++) {
            String str = (String) sql.getAllUsernames("all").get(i);
            if (str.contains(username)) {
                count++;
            }
        }
        if (sql.getAllUsernames("all").contains(username)) {
            username = username + count;
        }

        newTherapist(firstname, surname, username, password, userType);
    }

    public void newTherapist(String f, String s, String u, String p, String t) throws SQLException {
        sql.addToLogin(u, p, f, s, t);
        //ReadWriteFile.updateLoginFile(f, s, u, p, t);
    }
}
