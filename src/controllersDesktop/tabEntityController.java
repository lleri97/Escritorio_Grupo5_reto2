/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.Company;
import entitiesModels.User;
import entitiesModels.UserPrivilege;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javax.ws.rs.core.GenericType;
import servicesRestfull.CompanyClientService;
import utils.UtilsWindows;

/**
 * FXML Controller class
 *
 * @author Fran
 */
public class tabEntityController {

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
            String mod = "";
            lanzarNewEntityWindow(company, mod);
        });

        btnModifyEntity.setOnAction((event) -> {
            Company company = new Company();
            company = (Company) tableEntity.getSelectionModel().getSelectedItem();
            String mod = "modify";
            lanzarNewEntityWindow(company, mod);
        });

        btnSearchEntity.setOnAction((event) -> {
            insertData();
        });

        btnDeleteEntity.setOnAction((event) -> {
            Company companyDelete = new Company();
            companyDelete = (Company) tableEntity.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("¿Está seguro que desea borrar la empresa seleccionada?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                companyClient.remove(companyDelete.getId());
                insertData();
                LOGGER.info("Empresa borrada con exito.");
            }

        });

        tableEntity.getSelectionModel().selectedItemProperty().addListener(this::handleEntityTabSelectionChanged);
        LOGGER.info("Panel de administracion de entidades cargado");
    }

    /**
     * Metodo que relaciona la tabla con acciones
     *
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
     *
     * @param entity objeto del tipo Company
     */
    public void lanzarNewEntityWindow(Company entity, String mod) {
        LOGGER.info("Lanzando vetana de nueva entidad");
        try {
            NewCompanyController controller = new NewCompanyController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/GU_NewCompany.fxml"));
            Parent root = (Parent) loader.load();
            controller = ((NewCompanyController) loader.getController());
            controller.initStage(root, entity, mod);
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
