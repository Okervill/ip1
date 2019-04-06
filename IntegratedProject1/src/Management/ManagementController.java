/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Management;

import ManageService.ManageService;
import NewEmployee.NewEmployee;
import NewService.NewService;
import EditEmployee.EditEmployee;
import ManagerHolidays.ManagerHolidays;
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
    @FXML
    private Button holidays;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void newEmployeeLoad(ActionEvent event) {
        SwitchWindow.switchWindow((Stage) newEmployee.getScene().getWindow(), new NewEmployee());
    }

    @FXML
    private void manageEmployeeLoad(ActionEvent event) {
        SwitchWindow.switchWindow((Stage) manageEmployee.getScene().getWindow(), new EditEmployee());
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

    @FXML
    private void manageHolidays(ActionEvent event) {
        SwitchWindow.switchWindow((Stage) manageService.getScene().getWindow(), new ManagerHolidays());
    }
    
}
