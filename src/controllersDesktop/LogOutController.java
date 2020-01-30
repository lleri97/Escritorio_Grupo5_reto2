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
    public void initStage(Parent root, User usu) throws IOException {
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
        dataItemDepart.setOnAction((event)->{
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

        txtNombreUsu.setText("Nombre completo: " + usu.getFullname());
        txtLogin.setText("Usuario: " + usu.getLogin());
        txtEntity.setText("Entidad/Empresa: " + usu.getCompany().getName().toString());
        txtPrivilege.setText("Tipo usuario: " + usu.getPrivilege().toString());
        txtEmail.setText("Email: " + usu.getEmail());
        //InputStream myInputStream = new ByteArrayInputStream(usu.getPhoto());
        //photoProfileImg.setImage(new Image(myInputStream));

        stage.show();
        stage.setOnCloseRequest((WindowEvent event) -> {
            // consume event
            event.consume();
            // show close dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Close Confirmation");
            alert.setHeaderText("Do you really want to quit?");
            alert.initOwner(stage);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                lanzarLoginWindow();
                stage.close();
            }
        });

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
     * Changes the anchor pane from the main frame and chages it for areas pane
     */
    public void createTabAreas() {
        try {
            tabAreasController tabAreas = new tabAreasController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/tabAreas.fxml"));
            AnchorPane pane = loader.load();
            contentPane.getChildren().setAll(pane);
            tabAreas = (tabAreasController) loader.getController();
            tabAreas.initStage(usuario);
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
            tabDepartment.initStage(usuario);
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
            tabUsers.initStage(usuario);
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
            tabDocuments.initStage(usuario);
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
            tabEntity.initStage(usuario);
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
            String mod = "modify";
            controller.initStage(root, usuario, mod);
        } catch (IOException ex) {
            Logger.getLogger(tabUsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadData(User usu) {
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
    }

    private void refreshProfile() {
        UserClientService client = new UserClientService();
        User usu = client.find(User.class, Integer.toString(usuario.getId()));
        loadData(usu);
    }

    public void lanzarLoginWindow() {
        try {
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
