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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yeray
 */
public class NewAreaController  {

    @FXML
    private Label lblNewArea;
    @FXML
    private TextField textFieldNameArea;
    @FXML
    private Label lblName;
    @FXML
    private Label lblError;
    @FXML
    private Label lblCompany;
    @FXML
    private Label lblDepartment;
    @FXML
    private Button btnAddArea;
    @FXML
    private Button btnCancel;
    Stage stage;

    /**
     * Initializes the controller class.
     */
       public void initStage(Parent root){
        
        Scene sceneNewArea = new Scene(root);
        stage = new Stage();
        stage.setScene(sceneNewArea);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        
        stage.show();
        
        
    }
    
}
