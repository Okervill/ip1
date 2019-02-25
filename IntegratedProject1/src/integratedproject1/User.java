/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integratedproject1;

/**
 *
 * @author Chris Lowton
 */
public class User {
    private static String username = null;
    private static String userType = null;
    
    public String getUsername() {
        return username;
    }
    
    public String getUserType() {
        return userType;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setUserType(String type) {
        this.userType = type;
    }
}
