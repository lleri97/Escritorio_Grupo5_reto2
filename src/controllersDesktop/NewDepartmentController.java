/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yeray
 */
public class NewDepartmentController{

    @FXML
    private Label lblNewDepartment;
    @FXML
    private TextField textFieldDepartment;
    @FXML
    private Label lblDepartmentName;
    @FXML
    private Label lblError;
    @FXML
    private ChoiceBox<?> choiceBoxCompany;
    @FXML
    private Label lblCompany;
    @FXML
    private Button btnAddDepartment;
    @FXML
    private Button btnCancel;
    
    private Stage stage;

    /**
     * Initializes the controller class.
     */
         public void initStage(Parent root){
        
        Scene sceneNewDepartment = new Scene(root);
        stage = new Stage();
        stage.setScene(sceneNewDepartment);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        
        stage.show();
        
        
    }
    
}
