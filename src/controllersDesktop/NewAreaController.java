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
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
public class NewAreaController {

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
    @FXML
    private ComboBox comboDepart;
    @FXML
    private ComboBox comboCompany;

    private User usuario;

    Stage stage;

    /**
     * Initializes the controller class.
     */
    public void initStage(Parent root, User usuario) {

        this.usuario = usuario;

        Scene sceneNewArea = new Scene(root);
        stage = new Stage();
        stage.setScene(sceneNewArea);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        btnCancel.setOnAction((value) -> {
            stage.close();
        });
        btnAddArea.setOnAction((value) -> {

        });
      //  cargarCompanias(usuario.getPrivilege());
        //cargarDepart(usuario.getPrivilege());

        stage.show();

    }

    private void cargarCompanias(UserPrivilege privilegio) {
        CompanyClientService service = new CompanyClientService();
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

    private void cargarDepart(UserPrivilege privilegio) {
        DepartmentClientService clientDepart = new DepartmentClientService();
        if (usuario.getPrivilege().equals(UserPrivilege.SUPERADMIN)) {
            //depart = clientDepart.findAll(new GenericType<List<Company>>()); //llamar servidor y cargar entidades
            List<Department> depart = Arrays.asList();
            comboCompany = new ComboBox((ObservableList) depart);
            comboCompany.getSelectionModel().selectFirst();

        } else if (usuario.getPrivilege().equals(UserPrivilege.COMPANYADMIN)) {
            List<Company> entities = Arrays.asList();
            comboCompany.getItems().clear();
            comboCompany.getItems().addAll(entities);
        }
    }

}
