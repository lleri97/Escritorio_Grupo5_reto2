/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.Department;
import entitiesModels.User;
import entitiesModels.UserPrivilege;
import entitiesModels.UserStatus;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
 * FXML Inicializacion de panel de departamentos
 *
 * @author Ruben
 */
public class tabDepartmentController {
    
    @FXML
    private AnchorPane tabDepart;
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
    
    private static final Logger LOGGER = Logger.getLogger(SignUpController.class.getPackage() + "." + SignUpController.class.getName());
    
    private Set departmentList;
    
    User usuario;

    /**
     * Metodo de inicializacion de ventanas fxml
     *
     * @param usuario
     */
    public void initStage(User usuario) {
        LOGGER.info("Inicializando panel de administracion de departamentos");
        this.usuario = usuario;
        if (usuario.getPrivilege() == UserPrivilege.USER) {
            btnNewDepartment.setVisible(false);
            btnNewDepartment.setDisable(true);
        }
        btnDeleteDepartment.setVisible(false);
        btnModifyDepartment.setVisible(false);
        tableDepartments.getSelectionModel().selectedItemProperty().addListener(this::handleDepartmentTabSelectionChanged);
        btnNewDepartment.setOnAction((event) -> {
            Department depart = new Department();
            String mod = "";
            lanzarNewDepartmentWindow(depart, mod);
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
        btnModifyDepartment.setOnAction((event) -> {
            Department depart = new Department();
            depart = (Department) tableDepartments.getSelectionModel().getSelectedItem();
            String mod = "modify";
            lanzarNewDepartmentWindow(depart, mod);
        });
        LOGGER.info("Panel cargado con exito");
    }
    /**
     * metodo que relaciona la tabla con botones
     * @param observable
     * @param olsValue
     * @param newValue 
     */
    public void handleDepartmentTabSelectionChanged(ObservableValue observable, Object olsValue, Object newValue) {
        btnDeleteDepartment.setVisible(true);
        btnModifyDepartment.setVisible(true);
        
    }
    /**
     * Metodo para lanzar la ventana de nuevo departamento
     * @param depart
     * @param mod 
     */
    public void lanzarNewDepartmentWindow(Department depart, String mod) {
        
        try {
            LOGGER.info("Lanzando vetnana de nuevo departamento");
            NewDepartmentController controller = new NewDepartmentController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/GU_NewDepartment.fxml"));
            Parent root = (Parent) loader.load();
            controller = ((NewDepartmentController) loader.getController());
            controller.initStage(root, usuario, depart, mod);
        } catch (IOException ex) {
        }
    }
    /**
     * insercion de datos
     */
    public void insertData() {
        LOGGER.info("Insertando datos en la tabla");
        columnID.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        
        departmentService = new DepartmentClientService();
        departmentList = new HashSet<Department>();
        departmentList = departmentService.FindAllDepartment(new GenericType<Set<Department>>() {
        });
        
        if (usuario.getPrivilege() == UserPrivilege.COMPANYADMIN || usuario.getPrivilege() == UserPrivilege.USER) {
            
            List<Department> list = new ArrayList<Department>(departmentList);
            ObservableList<Department> departList = FXCollections.observableArrayList();
            List<Department> departamentos = new ArrayList<Department>(usuario.getCompany().getDepartments());
            Collection listDepart = list.stream().filter(depart -> depart.getId() == usuario.getId()).collect(Collectors.toList());
            departList.addAll(listDepart);
            
            for (int i = 0; i < departamentos.size(); i++) {
                for (int j = 0; j < list.size(); j++) {
                    if (departamentos.get(i).getId() == list.get(j).getId()) {
                        departList.add(list.get(j));
                    }
                    
                }
                
            }
            tableDepartments.setItems(departList);
        } else {
            List<Department> list = new ArrayList<Department>(departmentList);
            ObservableList<Department> departList = FXCollections.observableArrayList(list);
            tableDepartments.setItems(departList);
            
        }
        LOGGER.info("Datos insertados con exito");
    }
    
}
