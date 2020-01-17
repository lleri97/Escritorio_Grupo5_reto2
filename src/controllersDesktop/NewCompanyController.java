/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.Company;
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
import servicesRestfull.CompanyClientService;

/**
 * FXML Controller class
 *
 * @author Yeray
 */
public class NewCompanyController {

    @FXML
    private Label lblNewArea;
    @FXML
    private TextField textFieldNameCompany;
    @FXML
    private Label lblName;
    @FXML
    private Label lblError;
    @FXML
    private TextField textFieldCif;
    @FXML
    private Label lblCif;
    @FXML
    private Button btnAddCompany;
    @FXML
    private Button btnCancel;
    
    private Company company;
    
    private CompanyClientService companyService;
    
    Stage stage;

    
 
     public void initStage(Parent root){
        
        Scene sceneNewEntity = new Scene(root);
        stage = new Stage();
        stage.setScene(sceneNewEntity);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        lblError.setVisible(false);
        
        btnCancel.setOnAction((event)->{
            stage.close();
        });
        btnAddCompany.setOnAction((event)->{
            addCompany();
        });
        
        
        stage.show();
        
        
    }
    
    public void addCompany(){
        company.setCif(textFieldCif.getText());
        company.setName(textFieldNameCompany.getText());
        
        
    }
}
