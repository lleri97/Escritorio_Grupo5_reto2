/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.User;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author 2dam
 */
public class SignUpController {
    
    @FXML
    private TextField textEmail;
    @FXML
    private TextField textLogin;
    @FXML
    private TextField textFullName;
    @FXML
    private ComboBox comboEntity;
    @FXML
    private ComboBox comboType;
    @FXML
    private DatePicker datePickerBirthday;
    @FXML
    private Button btnAddPick;
    @FXML
    private Button btnCreateUser;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnHelpSignUp;
    
    private User usu;
    
     private Stage stage;
     
      public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public void initStage(Parent root , User usu){
   
        Scene sceneSignUp = new Scene(root);
        stage = new Stage();
        stage.setScene(sceneSignUp);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        
        stage.show();
        
        
    }
}