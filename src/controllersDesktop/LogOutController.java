/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.User;
import entitiesModels.UserPrivilege;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;
import servicesRestfull.UserClientService;
import utils.UtilsWindows;

/**
 * LogOutController Class
 *
 * @author Yeray
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
    private MenuBar menuBarMain;
    @FXML
    private Menu menuMenu;
    @FXML
    private MenuItem dataItemUsers;
    @FXML
    private MenuItem dataItemEntity;
    @FXML
    private MenuItem dataItemDocs;
    @FXML
    private MenuItem dataItemDepart;
    @FXML
    private MenuItem dataItemArea;
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
    @FXML
    private ImageView photoProfileImg;
    @FXML
    private Button btnRefreshProfile;

    private UtilsWindows util;

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
     * Metodo de inicializacion de la ventana principal
     *
     * @param root 
     * @param client 
     * @param user 
     */
    public void initStage(Parent root, User usu) throws IOException {
        LOGGER.info("inicializando vetnta principal");
        util = new UtilsWindows();
        dataItemUsers = new MenuItem("Usuario");
        dataItemUsers.setId("dataItemUsers");
        dataItemUsers.setOnAction((event) -> {
            createTabUsers();
        });
        if (usu.getPrivilege() == UserPrivilege.USER) {
            btnUsers.setVisible(false);
            btnUsers.setDisable(true);
            dataItemUsers.setDisable(true);
            dataItemUsers.setVisible(false);
        }

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
        btnRefreshProfile.setOnAction(this::handleButtonAction);
        cm = new ContextMenu();
        cm.setId("cm");

        dataItemEntity = new MenuItem("Entidades");
        dataItemEntity.setId("dataItemEntity");
        dataItemEntity.setOnAction((event) -> {
            createTabEntity();
        });
        dataItemDocs = new MenuItem("Documentos");
        dataItemDocs.setId("dataItemDocs");
        dataItemDocs.setOnAction((event) -> {
            createTabDocuments();
        });
        dataItemDepart = new MenuItem("Departamentos");
        dataItemDepart.setId("dataItemDepart");
        dataItemDepart.setOnAction((event) -> {
            createTabDepartments();
        });
        dataItemArea = new MenuItem("Areas");
        dataItemArea.setId("dataItemArea");
        dataItemArea.setOnAction((event) -> {
            createTabAreas();
        });

        cm.getItems().addAll(dataItemUsers, dataItemEntity, dataItemDocs, dataItemDepart, dataItemArea);
        paneUser.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                cm.show(paneUser, e.getScreenX(), e.getScreenY());
            }
        });

        loadData(usu);
        LOGGER.info("Ventana inicializada");

        stage.show();
        stage.setOnCloseRequest((WindowEvent event) -> {
            LOGGER.info("Cerrando la ventana principal");
            // consume event
            event.consume();
            // show close dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmacion de cierre");
            alert.setHeaderText("Realmente desea salir?");
            alert.initOwner(stage);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                lanzarLoginWindow();
                stage.close();
                LOGGER.info("ventana cerrada");
            }
            
        });


        //Menu events
        closeSessionItem.setOnAction((event) -> {
            LOGGER.info("Cerrando perfil de usuario");
            util.alertConfirmation("Confirmacion", "Realmente desea cerrar sesion?");
            lanzarLoginWindow();
            stage.close();

        });

        exitItem.setOnAction((event) -> {
            LOGGER.info("Cerrando la aplicacion");
            util.alertConfirmation("Confirmacion", "Realmente desea salir de la aplicacion?");
            Platform.exit();
        });

    }
    /**
     * Metodo para relacionar los botones del menu con acciones
     * @param event 
     */
    public void menuButtonAction(ActionEvent event) {
        if (event.getSource() == dataItemArea) {
            createTabAreas();
        } else if (event.getSource() == dataItemDepart) {
            createTabDepartments();
        } else if (event.getSource() == dataItemDocs) {
            createTabDocuments();
        } else if (event.getSource() == dataItemEntity) {
            createTabEntity();
        } else if (event.getSource() == dataItemUsers) {
            createTabUsers();
        }
    }
    /**
     * Metodo para relacionar botones de la interfaz con acciones
     * @param event 
     */
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
        } else if ((Button) event.getSource() == btnRefreshProfile) {
            refreshProfile();
        }

    }

    /**
     * Crea el panel de areas para su gestion
     */
    public void createTabAreas() {
        try {
            tabAreasController tabAreas = new tabAreasController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/tabAreas.fxml"));
            AnchorPane pane = loader.load();
            contentPane.getChildren().setAll(pane);
            tabAreas = (tabAreasController) loader.getController();
            tabAreas.initStage(usuario);
            LOGGER.info("Creando panel de areas");
        } catch (IOException ex) {
            Logger.getLogger(LogOutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Crea el panel de departamentos para su gestion
     */
    public void createTabDepartments() {
        try {
            tabDepartmentController tabDepartment = new tabDepartmentController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/tabDepartments.fxml"));
            AnchorPane pane = loader.load();
            contentPane.getChildren().setAll(pane);
            tabDepartment = (tabDepartmentController) loader.getController();
            tabDepartment.initStage(usuario);
            LOGGER.info("creando panel de departamentos");
        } catch (IOException ex) {
            Logger.getLogger(LogOutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Crea el panel de usuarios para su gestion
     */
    private void createTabUsers() {
        try {
            tabUsersController tabUsers = new tabUsersController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/tabUsers.fxml"));
            AnchorPane pane = loader.load();
            contentPane.getChildren().setAll(pane);
            tabUsers = (tabUsersController) loader.getController();
            tabUsers.initStage(usuario);
            LOGGER.info("Creando panel de usuarios");
        } catch (IOException ex) {
            Logger.getLogger(LogOutController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Crea el panel de documentos para su gestion
     */
    private void createTabDocuments() {
        try {
            tabDocumentsController tabDocuments = new tabDocumentsController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/tabDocuments.fxml"));
            AnchorPane pane = loader.load();
            contentPane.getChildren().setAll(pane);
            tabDocuments = (tabDocumentsController) loader.getController();
            tabDocuments.initStage(usuario, stage);
            LOGGER.info("Creando panel de documentos");
        } catch (IOException ex) {
            Logger.getLogger(LogOutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Crea el panel de entidades para su gestion
     */
    private void createTabEntity() {
        try {
            tabEntityController tabEntity = new tabEntityController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/tabEntity.fxml"));
            AnchorPane pane = loader.load();
            contentPane.getChildren().setAll(pane);
            tabEntity = (tabEntityController) loader.getController();
            tabEntity.initStage(usuario);
            LOGGER.info("Creando panel de compañias");
        } catch (IOException ex) {
            Logger.getLogger(LogOutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Abre la ventana de modificacion de usuario
     */
    private void openModifyUserWindow() {
        try {
            SignUpController controller = new SignUpController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/GU02_SignUp.fxml"));
            Parent root = (Parent) loader.load();
            controller = ((SignUpController) loader.getController());
            String mod = "modify";
            controller.initStage(root, usuario, mod);
            LOGGER.info("Abriendo ventana de modificacion de usuarios");
        } catch (IOException ex) {
            Logger.getLogger(tabUsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Carga los datos de el usuario
     * @param usu 
     */
    private void loadData(User usu) {
        LOGGER.info("Cargando datos del usuario");
        txtNombreUsu.setText("Nombre completo: " + usu.getFullname());
        txtLogin.setText("Usuario: " + usu.getLogin());
        if (usu.getCompany() != null) {
            txtEntity.setText("Entidad/Empresa: " + usu.getCompany().getName().toString());
        } else {
            txtEntity.setText("Entidad/Empresa: ADMINISTRADOR - 2DAM2CURIOUS");
        }
        txtPrivilege.setText("Tipo usuario: " + usu.getPrivilege().toString());
        txtEmail.setText("Email: " + usu.getEmail());
        if (usu.getPhoto() != null) {
            InputStream myInputStream = new ByteArrayInputStream(usu.getPhoto());
            photoProfileImg.setImage(new Image(myInputStream));
        }
        LOGGER.info("Datos del usuario cargados");
    }
    /**
     * Refresca el panel de los datos del usuario
     */
    private void refreshProfile() {
        UserClientService client = new UserClientService();
        User usu = client.find(User.class, Integer.toString(usuario.getId()));
        loadData(usu);
        LOGGER.info("Datos de perfil recargados");
    }
    /**
     * Lanza la ventana de inicio
     */
    public void lanzarLoginWindow() {
        try {
            LOGGER.info("Lanzando ventana de inicio");
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxmlWindows/GU01_Login.fxml"));
            Parent root = (Parent) loader.load();
            //Get controller for graph
            LoginController stageController = ((LoginController) loader.getController());
            //Set a reference for Stage
            stageController.setStage(stage);
            //Initializes primary stage
            stageController.initStage(root);
        } catch (IOException ex) {
            LOGGER.warning(ex.getMessage());
        }
    }

}
