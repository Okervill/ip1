/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewPatient;

import integratedproject1.ReadWriteFile;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author choco
 */
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
    private Button search;
    @FXML
    private ChoiceBox<String> multiplePatients;
    @FXML
    private TextField searchFirst;
    @FXML
    private TextField searchLast;
    @FXML
    private TextField searchPost;
    @FXML
    private Label multifind;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        multiplePatients.setVisible(false);
        multifind.setVisible(false);
    }

    /**@FXML
    private void search(ActionEvent event) throws IOException {

        firstname.setText("");
        surname.setText("");
        email.setText("");
        mobno.setText("");
        dob.setText("");
        gender.setText("");
        postcode.setText("");
        patientNo.setText("");

        multiplePatients.setVisible(false);
        multifind.setVisible(false);

        String searchFname = searchFirst.getText();
        String searchLname = searchLast.getText();
        String searchPostcode = searchPost.getText();
        ArrayList patientData = null;

        if (ReadWriteFile.getPatientData(searchFname, searchLname, searchPostcode).size() < 8) {
            return;
        } else {
            patientData = ReadWriteFile.getPatientData(searchFname, searchLname, searchPostcode);
        }

        int patientsFound = Integer.valueOf((String) patientData.get(patientData.size() - 1));
        int temp = (patientsFound - 1) * 8;
        System.out.println(patientsFound + " " + patientData);

        if (patientsFound > 1) {
            ObservableList<String> patients = FXCollections.observableArrayList();
            for (int i = 0; i <= temp; i += 8) {
                patients.add((String) patientData.get(i + 0) + " " + (String) patientData.get(i + 1) + " " + (String) patientData.get(i + 2));
            }
            multiplePatients.setVisible(true);
            multifind.setVisible(true);
            multiplePatients.setItems(patients);
        } else {
            firstname.setText((String) patientData.get(0));
            surname.setText((String) patientData.get(1));
            email.setText((String) patientData.get(2));
            mobno.setText((String) patientData.get(3));
            dob.setText((String) patientData.get(4));
            gender.setText((String) patientData.get(5));
            postcode.setText((String) patientData.get(6));
            patientNo.setText((String) patientData.get(7));
        }

    }**/

    @FXML
    private void loadPatient(MouseEvent event) {

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
}
