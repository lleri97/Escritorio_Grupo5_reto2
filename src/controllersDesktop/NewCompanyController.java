/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.Company;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
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
    private BorderPane paneNewCompany;
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

    private CompanyClientService companyService;

    Stage stage;

    public void initStage(Parent root, Company company) {

        if (company.getName() != "") {
            textFieldNameCompany.setText(company.getName());
            textFieldCif.setText(company.getCif());
            btnAddCompany.setText("Confirmar");

            btnAddCompany.setOnAction((event) -> {
                Company comp = new Company();
                comp = company;
                comp.setCif(textFieldCif.getText());
                comp.setName(textFieldNameCompany.getText());
                companyService.edit(company);
            });
        } else {
            btnAddCompany.setOnAction((event) -> {
                addCompany();
            });
        }

        Scene sceneNewEntity = new Scene(root);
        stage = new Stage();
        stage.setScene(sceneNewEntity);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        lblError.setVisible(false);

        btnCancel.setOnAction((event) -> {
            stage.close();
        });

        stage.show();

    }

    public void addCompany() {
        Company comp = new Company();
        companyService = new CompanyClientService();

        comp.setCif(textFieldCif.getText());
        comp.setName(textFieldNameCompany.getText());
        companyService.create(comp);

    }
}
