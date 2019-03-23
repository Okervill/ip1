/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Management;

import ManageService.ManageService;
import NewEmployee.AddTherapist;
import NewService.NewService;
import ViewTherapist.ViewTherapist;
import integratedproject1.SwitchWindow;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 */
public class ManagementController implements Initializable {

    @FXML
    private Button newEmployee;
    @FXML
    private Button manageEmployee;
    @FXML
    private Button newService;
    @FXML
    private Button manageService;
    @FXML
    private Button close;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void newEmployeeLoad(ActionEvent event) {
        SwitchWindow.switchWindow((Stage) newEmployee.getScene().getWindow(), new AddTherapist());
    }

    @FXML
    private void manageEmployeeLoad(ActionEvent event) {
        SwitchWindow.switchWindow((Stage) manageEmployee.getScene().getWindow(), new ViewTherapist());
    }

    @FXML
    private void newServiceLoad(ActionEvent event) {
        SwitchWindow.switchWindow((Stage) newService.getScene().getWindow(), new NewService());
    }

    @FXML
    private void manageServiceLoad(ActionEvent event) {
        SwitchWindow.switchWindow((Stage) manageService.getScene().getWindow(), new ManageService());
    }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
    
}
