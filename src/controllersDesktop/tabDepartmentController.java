/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.Department;
import entitiesModels.User;
import entitiesModels.UserPrivilege;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javax.ws.rs.core.GenericType;
import servicesRestfull.DepartmentClientService;

/**
 * FXML Controller class
 *
 * @author Yeray
 */
public class tabDepartmentController {

    @FXML
    private Button btnNewDepartment;
    @FXML
    private Button btnSearchDepartment;
    @FXML
    private TableView tableDepartments;
    @FXML
    private TableColumn columnID;
    @FXML
    private TableColumn columnName;
    @FXML
    private Button btnDeleteDepartment;
    @FXML
    private Button btnModifyDepartment;

    private DepartmentClientService departmentService;

    private Set departmentList;

    User usuario;

    public void initStage(User usuario) {

        this.usuario = usuario;
        if (usuario.getPrivilege() == UserPrivilege.USER) {
            btnNewDepartment.setVisible(false);
            btnNewDepartment.setDisable(true);
        }
        btnDeleteDepartment.setVisible(false);
        btnModifyDepartment.setVisible(false);
        tableDepartments.getSelectionModel().selectedItemProperty().addListener(this::handleDepartmentTabSelectionChanged);
        btnNewDepartment.setOnAction((event) -> {
            lanzarNewDepartmentWindow();
        });
        btnSearchDepartment.setOnAction((event) -> {
            insertData();
        });
        btnDeleteDepartment.setOnAction((event) -> {
            departmentService = new DepartmentClientService();
            Department deleteDepart = new Department();
            deleteDepart = (Department) tableDepartments.getSelectionModel().getSelectedItem();

            departmentService.remove(deleteDepart.getId());
            insertData();
        });

    }

    public void handleDepartmentTabSelectionChanged(ObservableValue observable, Object olsValue, Object newValue) {
        btnDeleteDepartment.setVisible(true);
        btnModifyDepartment.setVisible(true);

    }

    public void lanzarNewDepartmentWindow() {

        try {
            NewDepartmentController controller = new NewDepartmentController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/GU_NewDepartment.fxml"));
            Parent root = (Parent) loader.load();
            controller = ((NewDepartmentController) loader.getController());
            controller.initStage(root, usuario);
        } catch (IOException ex) {
        }
    }

    public void insertData() {
        columnID.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        departmentService = new DepartmentClientService();
        departmentList = new HashSet<Department>();
        departmentList = departmentService.FindAllDepartment(new GenericType<Set<Department>>() {
        });

        List<Department> list = new ArrayList<Department>(departmentList);
        ObservableList<Department> departList = FXCollections.observableArrayList(list);
        tableDepartments.setItems(departList);
                
        
    }

}
