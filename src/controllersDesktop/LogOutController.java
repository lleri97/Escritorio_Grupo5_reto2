/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * LogOutController Class
 *
 * @author Ruben
 */
public class LogOutController {

    @FXML
    private AnchorPane paneUser;
    @FXML
    private Label txtNombreUsu;
    @FXML
    private Label txtLogin;
    @FXML
    private Label txtEmail;
    @FXML
    private Label txtPrivilege;
    @FXML
    private Label txtEntity;
    @FXML
    private MenuItem closeSessionItem;
    @FXML
    private MenuItem exitItem;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private Button btnUsers;
    @FXML
    private Button btnEntity;
    @FXML
    private Button btnDocuments;
    @FXML
    private Button btnDepartments;
    @FXML
    private Button btnAreas;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnCloseConnection;
    @FXML
    private Button btnExit;

    ContextMenu cm;

    User usuario;

    private static final Logger LOGGER = Logger.getLogger(LogOutController.class.getPackage() + "." + LogOutController.class.getName());

    Stage stage;

    /**
     * Get a stage attribute
     *
     * @return stage data
     */
    public Stage getStage() {

        return stage;
    }

    /**
     * Set a Stage in stage attribute
     *
     * @param stage The stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Method to initialize JavaFX window
     *
     * @param root Parent will be used
     * @param client Client will be used
     * @param user User that contains the profile data
     */
    public void initStage(Parent root, User usu) {
        Scene scene = new Scene(root);
        usuario = new User();
        usuario = usu;
        //The window starts
        stage = new Stage();

        // Keyboard shortcuts
        KeyCombination CloseSession = new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN);
        KeyCombination Exit = new KeyCodeCombination(KeyCode.X, KeyCombination.ALT_DOWN);
        Mnemonic closeSession = new Mnemonic(btnCloseConnection, CloseSession);
        Mnemonic exit = new Mnemonic(btnExit, Exit);
        scene.addMnemonic(exit);
        scene.addMnemonic(closeSession);

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("LogOut");
        stage.setResizable(false);

        btnAreas.setOnAction(this::handleButtonAction);
        btnUsers.setOnAction(this::handleButtonAction);
        btnEntity.setOnAction(this::handleButtonAction);
        btnDepartments.setOnAction(this::handleButtonAction);
        btnDocuments.setOnAction(this::handleButtonAction);
        btnModify.setOnAction(this::handleButtonAction);
        cm = new ContextMenu();
        MenuItem cmItemUser = new MenuItem("Usuario");
        cmItemUser.setOnAction((event) -> {
            createTabUsers();
        });
        MenuItem cmItemEntity = new MenuItem("Entidades");
        cmItemEntity.setOnAction((event) -> {
            createTabEntity();
        });
        MenuItem cmItemDocuments = new MenuItem("Documentos");
        cmItemDocuments.setOnAction((event) -> {
            createTabDocuments();
        });
        MenuItem cmItemDepart = new MenuItem("Departamentos");
        cmItemDepart.setOnAction((event) -> {
            createTabDepartments();
        });
        MenuItem cmItemAreas = new MenuItem("Areas");
        cmItemAreas.setOnAction((event) -> {
            createTabAreas();
        });
        cm.getItems().addAll(cmItemAreas,cmItemDepart,cmItemDocuments,cmItemEntity,cmItemUser);
        paneUser.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)->{
            if(e.getButton()== MouseButton.SECONDARY){
                cm.show(paneUser, e.getScreenX(),e.getScreenY());
            }
        });
        
        /*
        txtNombreUsu.setText("Nombre completo: "+usu.getFullname());
        txtLogin.setText("Usuario: "+usu.getLogin());
        txtEntity.setText("Entidad/Empresa"+usu.getCompany().getName().toString());
        txtPrivilege.setText("Tipo usuario: "+usu.getPrivilege().toString());
        txtEmail.setText("Email: "+usu.getEmail()); 
         */
        stage.show();

        LOGGER.info("Profile loaded successfully.");

        //Menu events
        closeSessionItem.setOnAction((event) -> {
            LOGGER.info("Closing user profile.");
            stage.close();
        });

        exitItem.setOnAction((event) -> {
            LOGGER.info("Closing application");
            Platform.exit();
        });

    }

    public void handleButtonAction(ActionEvent event) {

        if ((Button) event.getSource() == btnAreas) {
            createTabAreas();
        } else if ((Button) event.getSource() == btnUsers) {
            createTabUsers();
        } else if ((Button) event.getSource() == btnDepartments) {
            createTabDepartments();
        } else if ((Button) event.getSource() == btnDocuments) {
            createTabDocuments();
        } else if ((Button) event.getSource() == btnEntity) {
            createTabEntity();
        } else if ((Button) event.getSource() == btnModify) {
            openModifyUserWindow();
        }

    }

    /**
     * Changes the anchor pane from the main frame and chages it for areas pane
     */
    public void createTabAreas() {
        try {
            tabAreasController tabAreas = new tabAreasController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/tabAreas.fxml"));
            AnchorPane pane = loader.load();
            contentPane.getChildren().setAll(pane);
            tabAreas = (tabAreasController) loader.getController();
            tabAreas.inicializar(usuario);
        } catch (IOException ex) {
            Logger.getLogger(LogOutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Changes the anchor pane from the main frame and chages it for departments
     * pane
     */
    public void createTabDepartments() {
        try {
            tabDepartmentController tabDepartment = new tabDepartmentController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/tabDepartments.fxml"));
            AnchorPane pane = loader.load();
            contentPane.getChildren().setAll(pane);
            tabDepartment = (tabDepartmentController) loader.getController();
            tabDepartment.inicializar(usuario);
        } catch (IOException ex) {
            Logger.getLogger(LogOutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Changes the anchor pane from the main frame and chages it for users pane
     */
    private void createTabUsers() {
        try {
            tabUsersController tabUsers = new tabUsersController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/tabUsers.fxml"));
            AnchorPane pane = loader.load();
            contentPane.getChildren().setAll(pane);
            tabUsers = (tabUsersController) loader.getController();
            tabUsers.inicializar(usuario);
        } catch (IOException ex) {
            Logger.getLogger(LogOutController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Changes the anchor pane from the main frame and chages it for documents
     * pane
     */
    private void createTabDocuments() {
        try {
            tabDocumentsController tabDocuments = new tabDocumentsController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/tabDocuments.fxml"));
            AnchorPane pane = loader.load();
            contentPane.getChildren().setAll(pane);
            tabDocuments = (tabDocumentsController) loader.getController();
            tabDocuments.inicializar(usuario);
        } catch (IOException ex) {
            Logger.getLogger(LogOutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Changes the anchor pane from the main frame and chages it for entity pane
     */
    private void createTabEntity() {
        try {
            tabEntityController tabEntity = new tabEntityController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/tabEntity.fxml"));
            AnchorPane pane = loader.load();
            contentPane.getChildren().setAll(pane);
            tabEntity = (tabEntityController) loader.getController();
            tabEntity.inicializar(usuario);
        } catch (IOException ex) {
            Logger.getLogger(LogOutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Open a new window to modify the user
     */
    private void openModifyUserWindow() {
        try {
            SignUpController controller = new SignUpController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/GU02_SignUp.fxml"));
            Parent root = (Parent) loader.load();
            controller = ((SignUpController) loader.getController());
            controller.initStage(root, usuario);
        } catch (IOException ex) {
            Logger.getLogger(tabUsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
