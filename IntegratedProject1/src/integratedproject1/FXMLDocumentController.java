/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integratedproject1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author patrick
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private Button button;
    @FXML
    private TextField inputuser;
    @FXML
    private TextField inputpass;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @FXML
    private void login(ActionEvent event) {
        System.out.println("Login attempted.");
        
        try (Scanner s = new Scanner(new File("LoginData.txt"))) {
            ArrayList<String> loginData = new ArrayList<String>();
            while (s.hasNext()){
                loginData.add(s.next());
            }
            s.close();
            
            int sectionLength = 1;
            boolean foundMetadata = false;
            int currentIndex = 0;
            
            
            while (!foundMetadata) {
                String line = loginData.get(currentIndex);
                if (line.contains("--!>")) 
                {
                    //Metadata end
                    foundMetadata = true;
                } else if (line.contains("SectionLength:")) {
                    sectionLength = Integer.parseInt(line.split(" ")[1]);
                }
                currentIndex++;
            }
            
            boolean foundUsername = false;
            
            while (!foundUsername) {
                String line = loginData.get(currentIndex);
                if (line.contains("username:")) {
                    //Username line found
                    //If username matches check password
                    System.out.println("Found username");
                    currentIndex += sectionLength;
                } else {
                    currentIndex++;
                }
            }
        } catch (IOException ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
