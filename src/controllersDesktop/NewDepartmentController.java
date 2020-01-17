/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.Company;
import entitiesModels.Department;
import entitiesModels.User;
import entitiesModels.UserPrivilege;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import servicesRestfull.CompanyClientService;
import servicesRestfull.DepartmentClientService;

/**
 * FXML Controller class
 *
 * @author Yeray
 */
public class NewDepartmentController {

    @FXML
    private Label lblNewDepartment;
    @FXML
    private TextField textFieldDepartment;
    @FXML
    private Label lblDepartmentName;
    @FXML
    private Label lblError;
    @FXML
    private ComboBox comboCompany;
    @FXML
    private Label lblCompany;
    @FXML
    private Button btnAddDepartment;
    @FXML
    private Button btnCancel;

    private User usuario;
    private Department depart;
    private Stage stage;
    private CompanyClientService companyService;
    private DepartmentClientService departmentService;

    /**
     * Initializes the controller class.
     */
    public void initStage(Parent root, User usuario) {
        this.usuario = usuario;
        Scene sceneNewDepartment = new Scene(root);
        stage = new Stage();
        stage.setScene(sceneNewDepartment);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);

        btnCancel.setOnAction((event) -> {
            stage.close();
        });
        
        btnAddDepartment.setOnAction((event)->{
            depart.setName(textFieldDepartment.getText());
            depart.setCompanies(comboCompany.getItems());
            
            departmentService = new DepartmentClientService();
        });
      //  cargarCompanias(usuario.getPrivilege());
        lblError.setVisible(false);
        stage.show();
    }

    private void cargarCompanias(UserPrivilege privilegio) {
         companyService = new CompanyClientService();
        if (usuario.getPrivilege().equals(UserPrivilege.SUPERADMIN)) {
            //entities = clientCompany.findAll(new GenericType<List<Company>>()); //llamar servidor y cargar entidades
            List<Company> entities = Arrays.asList(usuario.getCompany());
            comboCompany = new ComboBox((ObservableList) entities);
            comboCompany.getSelectionModel().selectFirst();

        } else if (usuario.getPrivilege().equals(UserPrivilege.COMPANYADMIN)) {
            List<Company> entities = Arrays.asList(usuario.getCompany());
            comboCompany.getItems().clear();
            comboCompany.getItems().addAll(entities);
        }
    }

}
