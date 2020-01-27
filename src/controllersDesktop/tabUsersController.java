/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.Document;
import entitiesModels.User;
import entitiesModels.UserPrivilege;
import java.io.IOException;
import static java.sql.Types.NULL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 *
 * @author Yeray
 */
public class tabUsersController {

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

    private Set<User> usersData;
    private User usuario;

    public User getUsu() {
        return usuario;
    }

    public void setUsu(User usu) {
        this.usuario = usu;
    }

    public void initStage(User usuario) {
        
        this.usuario = usuario;

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

    }

    public void insertData(User usuario) {

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
        if (usuario.getPrivilege() == UserPrivilege.SUPERADMIN) {
            List<User> list = new ArrayList<User>(usersData);
            ObservableList<User> userList = FXCollections.observableArrayList(list);
            tableUsers.setItems(userList);
        } else if (usuario.getPrivilege() == UserPrivilege.COMPANYADMIN) {
            List<User> list = new ArrayList<User>(usersData);
            ObservableList<User> userList = FXCollections.observableArrayList();
            for (int i = 0; i <usersData.size(); i++) {
                if (list.get(i).getCompany() != null) {
                    if (usuario.getCompany().getId() == list.get(i).getCompany().getId()) {
                        userList.add(list.get(i));
                    }
                }
               
                  
               
            }

            tableUsers.setItems(userList);
        }
    }

    public void lanzarNewUserWindow() {

        try {
            SignUpController controller = new SignUpController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/GU02_SignUp.fxml"));
            Parent root = (Parent) loader.load();
            controller = ((SignUpController) loader.getController());
            String mod = "modify";
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
}
