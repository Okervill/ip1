/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import MainScreen.Mainscreen;
import integratedproject1.ReadWriteFile;
import integratedproject1.SwitchWindow;
import Animation.Shaker;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Button button;
    @FXML
    private TextField inputuser;
    @FXML
    private TextField inputpass;

    @FXML
    private void login(ActionEvent event) throws IOException {

        String user = inputuser.getText();
        String pass = inputpass.getText();

        if (user.length() < 1 || pass.length() < 1) {
            Shaker shaker = new Shaker(button);
            shaker.shake();
            return;
        }

        boolean login = false;
        String userType;

        try {

            for (int i = 0; i < ReadWriteFile.getUsernames("all").size(); i++) {
                //Check username and password are correct
                if (user.equals(ReadWriteFile.getUsernames("all").get(i)) && pass.equals(ReadWriteFile.getLoginData(user).get(1))) {
                    login = true;
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (login) {
            //Swap Scene
            userType = (String) ReadWriteFile.getLoginData(user).get(4);
            SwitchWindow.switchWindow((Stage) button.getScene().getWindow(), new Mainscreen(user, userType));
        } else {
            Shaker shaker = new Shaker(button);
            shaker.shake();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
