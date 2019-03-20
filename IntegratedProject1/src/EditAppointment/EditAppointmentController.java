/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EditAppointment;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


public class EditAppointmentController implements Initializable {

    int app;
    @FXML
    private Label testDisplay;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void setData(String a){
        testDisplay.setText(a);
    }
    
    public EditAppointmentController(){
    }
}
