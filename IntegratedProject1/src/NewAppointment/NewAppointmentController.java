/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NewAppointment;

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
import javafx.stage.Stage;

public class NewAppointmentController implements Initializable {

    @FXML
    private Label appointmentNumber;
    @FXML
    private ChoiceBox<String> chooseTherapist;
    @FXML
    private Button close;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ArrayList<String> allTherapists = new ArrayList<>();
        ObservableList<String> therapists = FXCollections.observableArrayList();
        try {
            allTherapists = ReadWriteFile.getAllTherapists();
        } catch (IOException ex) {
            Logger.getLogger(NewAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < allTherapists.size(); i++) {
            therapists.add(allTherapists.get(i));
        }
        chooseTherapist.setItems(therapists);

    }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
}
