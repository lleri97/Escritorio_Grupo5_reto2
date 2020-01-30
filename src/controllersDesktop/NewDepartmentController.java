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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javax.ws.rs.core.GenericType;
import servicesRestfull.AreaClientService;
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
    public void initStage(Parent root, User usuario, Department department, String mod) {

        if (mod == "modify") {
            textFieldDepartment.setText(department.getName());
            Set<Company> entities = new HashSet<Company>();
            entities= department.getCompanies();
            if(entities!=null){
            comboCompany.getItems().add(entities);
            }
            btnAddDepartment.setText("confirmar");
            btnAddDepartment.setOnAction((event) -> {
                Department depart = new Department();
                department.setName(textFieldDepartment.getText());
             
                departmentService.edit(depart);
            });

        } else {
            btnAddDepartment.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    depart = new Department();
                    depart.setName(textFieldDepartment.getText().toString());
                    Set<Company> comp = new HashSet<Company>();
                    comp.add((Company) comboCompany.getValue());
                    depart.setCompanies(comp);
                    departmentService = new DepartmentClientService();
                    departmentService.create(depart);
                }
            });

        }
        this.usuario = usuario;
        Scene sceneNewDepartment = new Scene(root);
        stage = new Stage();
        stage.setScene(sceneNewDepartment);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);

        btnCancel.setOnAction((event) -> {
            stage.close();
        });

        cargarCompanias(usuario.getPrivilege());
        lblError.setVisible(false);
        stage.show();
    }

    private void cargarCompanias(UserPrivilege privilegio) {
        companyService = new CompanyClientService();
        if (usuario.getPrivilege().equals(UserPrivilege.SUPERADMIN)) {
            Set<Company> entities = new HashSet<Company>(); //llamar servidor y cargar entidades
            entities = companyService.findAll(new GenericType<Set<Company>>() {
            });

            //llamar servidor y cargar entidades
            comboCompany.getItems().addAll(entities);
            comboCompany.getSelectionModel().selectFirst();

        } else if (usuario.getPrivilege().equals(UserPrivilege.COMPANYADMIN)) {
            Company entities = usuario.getCompany();
            comboCompany.getItems().clear();
            comboCompany.getItems().addAll(entities);
        }
    }
}
