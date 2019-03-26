/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import MainScreen.Mainscreen;
import integratedproject1.SwitchWindow;
import Animation.Shaker;
import SQL.SQLHandler;
import integratedproject1.Hash;
import integratedproject1.User;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    SQLHandler sql = new SQLHandler();

    @FXML
    private Label label;
    @FXML
    private Button button;
    @FXML
    private TextField inputuser;
    @FXML
    private TextField inputpass;

    @FXML
    private void login(ActionEvent event) throws SQLException {

        String userType;
        String user = inputuser.getText();
        String pass = inputpass.getText();
        Hash h1 = new Hash();
        ArrayList<String> userinfo = sql.search("login", "username", user);
        
        //Login quick checks, empty user/pass. Then username exists, then user is active. Finally check password is correct.
        if (user.isEmpty() || pass.isEmpty() || !sql.checkUserExists(user) || !sql.checkUserActive(user) || !h1.verifyHash(pass, userinfo.get(1))) {
            loginFailed();
        } else {
            userType = userinfo.get(4);
            User currentUser = new User();
            currentUser.setUsername(user);
            currentUser.setUserType(userType);
            SwitchWindow.switchWindow((Stage) button.getScene().getWindow(), new Mainscreen(user, userType));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inputuser.requestFocus();
    }

    @FXML
    private void swapFocusToPassword(ActionEvent event) {
        //Pressing enter on the username field will select the password field
        inputpass.requestFocus();
    }

    public void loginFailed() {
        Shaker shaker = new Shaker(button);
        shaker.shake();
        inputuser.requestFocus();
    }

}
