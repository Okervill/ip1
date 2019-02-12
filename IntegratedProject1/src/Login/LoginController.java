/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import MainScreen.Mainscreen;
import integratedproject1.ReadWriteFile;
import integratedproject1.SwitchWindow;
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
        
        if(user.length() < 1 || pass.length() < 1){
            return;
        }

        boolean login = false;

        try {

            for (int i = 0; i < ReadWriteFile.getUsernames().size(); i++) {
                //Check username and password are correct
                if (user.equals(ReadWriteFile.getUsernames().get(i)) && pass.equals(ReadWriteFile.getData(user).get(1))) {
                    login = true;
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (login) {
            //Swap Scene
            System.out.println("Login Sucessful");
            SwitchWindow.switchWindow((Stage)button.getScene().getWindow(), new Mainscreen());
        } else {
            //Pop up error?
            SwitchWindow.switchWindow((Stage)button.getScene().getWindow(), new LoginFailed());
            System.out.println("Login Failed");
        }
    }
/**
        System.out.println("Login attempted.");

        try (Scanner s = new Scanner(new File("src/integratedproject1/LoginData.txt"))) {
            ArrayList<String> loginData = new ArrayList<>();
            while (s.hasNextLine()) {
                loginData.add(s.nextLine());
            }
            s.close();

            int sectionLength = 1;
            boolean foundMetadata = false;
            int currentIndex
                    = 0;

            while (!foundMetadata) {
                String line = loginData.get(currentIndex);
                if (line.contains("--!>")) { //Metadata end 
                    foundMetadata = true; 
                } else if (line.contains("SectionLength:")) {
                        sectionLength = Integer.parseInt(line.split(": ")[1]);
                    }
                    currentIndex++;
                }
            boolean foundUsername = false;

            while (!foundUsername && currentIndex < loginData.size()) {
                String line
                        = loginData.get(currentIndex);
                if (line.contains("Username:")) { //Usernameline found 
                    //If username matches check password 
                    String input = inputuser.getText();
                    String split = line.split(": ")[1];
                    if (inputuser.getText().equals(line.split(": ")[1])) { //username match,check password 
                        if (inputpass.getText().equals(loginData.get(currentIndex
                                + 1).split(": ")[1])) { //login details correct switch scene
                            System.out.println("Logged in.");
                            label.setText("Logged in.");
                        }
                    } else {
                        //Found a username but no match, skip to next 
                        currentIndex += sectionLength;
                    }
                } else {
                    currentIndex++;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex
            );
            System.out.println("Exception");
        }
    }
**/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
