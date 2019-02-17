/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewPatient;

import MainScreen.Mainscreen;
import integratedproject1.SwitchWindow;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class ViewPatientController implements Initializable {

    @FXML
    private TextField firstname;
    @FXML
    private TextField gender;
    @FXML
    private TextField dob;
    @FXML
    private TextField mobno;
    @FXML
    private TextField email;
    @FXML
    private TextField surname;
    @FXML
    private TextField postcode;
    @FXML
    private TextField patientNo;
    @FXML
    private Button newAppointmentButton;
    @FXML
    private Button back;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setData(ArrayList<String> data) {
        firstname.setText(data.get(0));
        surname.setText(data.get(1));
        email.setText(data.get(2));
        mobno.setText(data.get(3));
        dob.setText(data.get(4));
        gender.setText(data.get(5));
        postcode.setText(data.get(6));
        patientNo.setText(data.get(7));
    }

    @FXML
    private void newAppointment(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/NewAppointment/NewAppointment.fxml"));
        Parent root = (Parent) loader.load();
        Stage secondStage = new Stage();
        secondStage.setScene(new Scene(new HBox(root)));
        secondStage.show();
    }

    @FXML
    private void back(ActionEvent event) {

        SwitchWindow.switchWindow((Stage) back.getScene().getWindow(), new Mainscreen());
    }
}
