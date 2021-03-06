/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.Document;
import entitiesModels.User;
import entitiesModels.UserPrivilege;
import entitiesModels.UserStatus;
import java.io.IOException;
import static java.sql.Types.NULL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;
import servicesRestfull.DocumentClientService;
import servicesRestfull.UserClientService;
import utils.UtilsWindows;

/**
 *
 * @author Yeray
 */
public class tabUsersController {

    @FXML
    private AnchorPane tabUsers;
    @FXML
    private CheckBox chkBoxHabilitado;
    @FXML
    private CheckBox chkBoxDeshabilitado;
    @FXML
    private Button btnNewUser;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnModifyUser;
    @FXML
    private Button btnDeleteUser;
    @FXML
    private TableView tableUsers;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn emailColumn;
    @FXML
    private TableColumn companyColumn;
    @FXML
    private TableColumn lastAccessColumn;
    private static final Logger LOGGER = Logger.getLogger(SignUpController.class.getPackage() + "." + SignUpController.class.getName());

    private Set<User> usersData;
    private User usuario;
    private UtilsWindows util;

    public User getUsu() {
        return usuario;
    }

    public void setUsu(User usu) {
        this.usuario = usu;
    }

    /**
     * Metodo de inicializacion de panel de usuarios
     *
     * @param usuario objeto del tipo User
     */
    public void initStage(User usuario) {
        LOGGER.info("Inicializando panel de administracion de usuarios");
        util = new UtilsWindows();
        this.usuario = usuario;
        chkBoxHabilitado.setSelected(true);
        btnDeleteUser.setVisible(false);
        btnModifyUser.setVisible(false);

        btnNewUser.setOnAction((event) -> {
            lanzarNewUserWindow();
        });
        btnSearch.setOnAction((event) -> {
            insertData(usuario);
        });
        btnDeleteUser.setOnAction(this::handleButtonAction);
        btnModifyUser.setOnAction(this::handleButtonAction);

        tableUsers.getSelectionModel().selectedItemProperty().addListener(this::handleUsersTabSelectionChanged);
        LOGGER.info("Panel creado con exito");
    }

    /**
     * Metodo que inserta los datos en la tabla
     *
     * @param usuario objeto del tipo User
     */
    public void insertData(User usuario) {
        LOGGER.info("Insertando datos en la tabla");
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("fullname"));
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<>("Email"));
        companyColumn.setCellValueFactory(
                new PropertyValueFactory<>("company"));
        lastAccessColumn.setCellValueFactory(
                new PropertyValueFactory<>("lastAccess"));

        UserClientService userService = new UserClientService();
        usersData = new HashSet<User>();
        usersData = userService.findAll(new GenericType<Set<User>>() {
        });
        if (chkBoxHabilitado.isSelected() && chkBoxDeshabilitado.isSelected()) {
            chargeAllUsers();
        } else if (chkBoxDeshabilitado.isSelected()) {
            chargeDisabledUsers();
        } else if (chkBoxHabilitado.isSelected()) {
            chargeEnabledUsers();
        } else {
            util.alertInformation("No apareceran datos", "No se seleccionaron datos", "okButtonChkNoSelected");
        }
    }

    public void lanzarNewUserWindow() {

        try {
            SignUpController controller = new SignUpController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/GU02_SignUp.fxml"));
            Parent root = (Parent) loader.load();
            controller = ((SignUpController) loader.getController());
            String mod = "";
            controller.initStage(root, usuario, mod);
        } catch (IOException ex) {
            Logger.getLogger(tabUsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleUsersTabSelectionChanged(ObservableValue observable, Object olsValue, Object newValue) {
        btnDeleteUser.setVisible(true);
        btnModifyUser.setVisible(true);

    }

    public void handleButtonAction(ActionEvent event) {
        if ((Button) event.getSource() == btnModifyUser) {

            try {
                SignUpController controller = new SignUpController();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/GU02_SignUp.fxml"));
                Parent root = (Parent) loader.load();
                controller = ((SignUpController) loader.getController());
                String mod = "modify";
                User user = new User();
                user = (User) tableUsers.getSelectionModel().getSelectedItem();
                controller.initStage(root, user, mod);
            } catch (IOException ex) {
                Logger.getLogger(tabUsersController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if ((Button) event.getSource() == btnDeleteUser) {
            UserClientService userService = new UserClientService();
            User deleteUser = new User();
            deleteUser = (User) tableUsers.getSelectionModel().getSelectedItem();
            userService.remove(deleteUser.getId());
            insertData(usuario);
        }
    }

    public void chargeAllUsers() {
        LOGGER.info("Cargando todos los usuarios");
        if (usuario.getPrivilege() == UserPrivilege.SUPERADMIN) {
            List<User> list = new ArrayList<User>(usersData);
            ObservableList<User> userList = FXCollections.observableArrayList(list);
            tableUsers.setItems(userList);
        } else if (usuario.getPrivilege() == UserPrivilege.COMPANYADMIN) {
            LOGGER.info("Cargando los usuarios de la empresa");
            List<User> list = new ArrayList<User>(usersData);
            List<User> listUser = new ArrayList<User>();
            ObservableList<User> userList = FXCollections.observableArrayList();
            listUser = (List<User>) list.stream().filter(user -> user.getCompany().getCif().equals(usuario.getCompany().getCif())).collect(Collectors.toList());
            userList.addAll(listUser);
            tableUsers.setItems(userList);
        }
        LOGGER.info("Usuarios cargados con exito");

    }

    private void chargeDisabledUsers() {
        LOGGER.info("Cargando los usuarios deshabilitados");
        if (usuario.getPrivilege() == UserPrivilege.SUPERADMIN) {
            List<User> list = new ArrayList<User>(usersData);
            ObservableList<User> userList = FXCollections.observableArrayList();
            Collection listUser = list.stream().filter(user -> user.getStatus() == UserStatus.DISABLED).collect(Collectors.toList());
            userList.addAll(listUser);
            LOGGER.info("Usuarios cargados con exito");
            tableUsers.setItems(userList);
        } else if (usuario.getPrivilege() == UserPrivilege.COMPANYADMIN) {
            List<User> list = new ArrayList<User>(usersData);
            ObservableList<User> userList = FXCollections.observableArrayList();
            Collection listUser = list.stream().filter(user -> user.getStatus() == UserStatus.DISABLED).collect(Collectors.toList());
            userList.addAll(listUser);
            tableUsers.setItems(userList);
            LOGGER.info("Usuarios deshabilitados de la empresa cargados con exito");
        }
    }

    private void chargeEnabledUsers() {
        LOGGER.info("Cargando usuarios habilitados");
        if (usuario.getPrivilege() == UserPrivilege.SUPERADMIN) {
            List<User> list = new ArrayList<User>(usersData);
            ObservableList<User> userList = FXCollections.observableArrayList();
            Collection listUser = list.stream().filter(user -> user.getStatus() == UserStatus.ENABLED).collect(Collectors.toList());
            userList.addAll(listUser);
            tableUsers.setItems(userList);
        } else if (usuario.getPrivilege() == UserPrivilege.COMPANYADMIN) {
            LOGGER.info("Cargando usuarios de su empresa");
            List<User> list = new ArrayList<User>(usersData);
            ObservableList<User> userList = FXCollections.observableArrayList();
            Collection listUser = list.stream().filter(user -> user.getStatus() == UserStatus.ENABLED).collect(Collectors.toList());
            userList.addAll(listUser);
            tableUsers.setItems(userList);
        }
        LOGGER.info("Usuarios cargados con exito");

    }
}
