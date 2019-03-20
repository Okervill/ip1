/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewPatient;

import MainScreen.Mainscreen;
import NewAppointment.NewAppointment;
import SQL.SQLHandler;
import integratedproject1.SwitchWindow;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

    SQLHandler sql = new SQLHandler();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Platform.runLater(() -> {
            try {
                getAppointments();
            } catch (SQLException ex) {
                Logger.getLogger(ViewPatientController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
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

        SwitchWindow.switchWindow((Stage) newAppointmentButton.getScene().getWindow(), new NewAppointment(patientNo.getText()));
        
    }

    @FXML
    private void back(ActionEvent event) {

        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }

    public void getAppointments() throws SQLException {
        ArrayList<String> appointments = sql.search("appointment", "patientnumber", patientNo.getText());
        int numberAppointments = appointments.size() / sql.countFields("appointment");
    }
}
