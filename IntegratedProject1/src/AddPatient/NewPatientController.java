/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddPatient;

import integratedproject1.ReadWriteFile;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author patrick
 */
public class NewPatientController implements Initializable {

    @FXML
    private TextField firstname;
    @FXML
    private TextField surname;
    @FXML
    private DatePicker dob;
    @FXML
    private TextField email;
    @FXML
    private TextField postcode;
    @FXML
    private ChoiceBox<String> gender;
    @FXML
    private TextField contactno;
    @FXML
    private Button save;
    @FXML
    private Button discard;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        gender.setItems(FXCollections.observableArrayList("Female", "Male"));

    }

    @FXML
    private void save(ActionEvent event) throws IOException {

        String f = firstname.getText();
        String s = surname.getText();
        LocalDate d = dob.getValue();
        String e = email.getText();
        String pc = postcode.getText();
        String g = gender.getSelectionModel().getSelectedItem();
        String c = contactno.getText();

        if (f.isEmpty() || s.isEmpty() || e.isEmpty() || c.isEmpty() || gender.getSelectionModel().isEmpty()) {
            
            //Display error is details are not entered correctly
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Enter details!");
            alert.setContentText("You must enter details in all boxes");
            alert.showAndWait();
            
            return;
        }

        File file = new File("/src/integratedproject1/PatientFile.txt");

        int patNum = ReadWriteFile.countPatients() + 1;

        ReadWriteFile.updatePatientFile(f, s, e, c, d.toString(), g, pc, patNum);

        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) discard.getScene().getWindow();
        stage.close();
    }

}
