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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javax.ws.rs.core.GenericType;
import servicesRestfull.CompanyClientService;
import servicesRestfull.DepartmentClientService;

/**
 * FXML Controller class
 *
 * @author Fran
 */
public class tabEntityController {
    
    @FXML
    private AnchorPane tabEntity;
    @FXML
    private Button btnNewCompany;
    @FXML
    private Button btnSearchEntity;
    @FXML
    private Label lblSearchEntity;
    @FXML
    private TableView tableEntity;
    @FXML
    private TableColumn columnName;
    @FXML
    private TableColumn columnCIF;
    @FXML
    private TableColumn columnDepartment;
    @FXML
    private Button btnDeleteEntity;
    @FXML
    private Button btnModifyEntity;
    
    private Set<Company> companyList;
    private static final Logger LOGGER = Logger.getLogger(SignUpController.class.getPackage() + "." + SignUpController.class.getName());
    
    private CompanyClientService companyClient;
    
    private User usuario;

    /**
     * Metodo para inicializar ventanas de fxml
     *
     * @param usuario objeto del tipo User
     */
    public void initStage(User usuario) {
        LOGGER.info("Inicializando panel de administracion de entidades");
        this.usuario = usuario;
        btnDeleteEntity.setVisible(false);
        btnModifyEntity.setVisible(false);
        
        if (usuario.getPrivilege() == UserPrivilege.USER || usuario.getPrivilege() == UserPrivilege.COMPANYADMIN) {
            btnNewCompany.setDisable(true);
            btnNewCompany.setVisible(false);
            btnSearchEntity.setDisable(true);
            btnSearchEntity.setVisible(false);
            lblSearchEntity.setVisible(false);
            insertData();
            
        }
        
        btnNewCompany.setOnAction((event) -> {
            Company company = new Company();
            lanzarNewEntityWindow(company);
        });
        btnSearchEntity.setOnAction((event) -> {
            insertData();
        });
        btnDeleteEntity.setOnAction((event) -> {
            Company companyDelete = new Company();
            companyDelete = (Company) tableEntity.getSelectionModel().getSelectedItem();
            companyClient.remove(companyDelete.getId());
            insertData();
        });
        btnModifyEntity.setOnAction((event) -> {
            Company company = new Company();
            company = (Company) tableEntity.getSelectionModel().getSelectedItem();
            lanzarNewEntityWindow(company);
        });
        tableEntity.getSelectionModel().selectedItemProperty().addListener(this::handleEntityTabSelectionChanged);
        LOGGER.info("Panel de administracion de entidades cargado");
    }
    /**
     * Metodo que relaciona la tabla con acciones
     * @param observable tipo ObservableValue
     * @param olsValue tipo Object
     * @param newValue tipo Object
     */
    public void handleEntityTabSelectionChanged(ObservableValue observable, Object olsValue, Object newValue) {
        btnDeleteEntity.setVisible(true);
        btnModifyEntity.setVisible(true);
        
    }
    /**
     * Metodo ue lanza la ventana de nueva entidad
     * @param entity objeto del tipo Company
     */
    public void lanzarNewEntityWindow(Company entity) {
        LOGGER.info("Lanzando vetana de nueva entidad");
        try {
            NewCompanyController controller = new NewCompanyController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/GU_NewCompany.fxml"));
            Parent root = (Parent) loader.load();
            controller = ((NewCompanyController) loader.getController());
            controller.initStage(root, entity);
        } catch (IOException ex) {
            Logger.getLogger("Esto peta aqui");
        }
    }
    /**
     * Metodo de insercion de datos en la tabla
     */
    public void insertData() {
        columnCIF.setCellValueFactory(
                new PropertyValueFactory<>("cif"));
        columnName.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        columnDepartment.setCellValueFactory(
                new PropertyValueFactory<>("department"));
        ObservableList<Company> entityList;
        LOGGER.info("Insertando datos en la tabla");
        if (usuario.getPrivilege() == UserPrivilege.USER || usuario.getPrivilege() == UserPrivilege.COMPANYADMIN) {
            LOGGER.info("Cargando datos de entidades");
            entityList = FXCollections.observableArrayList();
            entityList.add(usuario.getCompany());
        } else {
            LOGGER.info("Cargando todas las entidades");
            companyClient = new CompanyClientService();
            companyList = new HashSet<Company>();
            companyList = companyClient.findAll(new GenericType<Set<Company>>() {
            });
            List<Company> list = new ArrayList<Company>(companyList);
            entityList = FXCollections.observableArrayList(list);
            
        }
        
        tableEntity.setItems(entityList);
        LOGGER.info("Datos cargados con exito");
    }
    
}
