/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.User;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import servicesRestfull.UserClientService;
import utils.EncryptionClientClass;
import utils.UtilsWindows;

/**
 *
 * @author Fran
 */
public class LoginController {

    @FXML
    private Button btnLogIn;
    @FXML
    private Hyperlink hplRecoverPassword;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField textFieldPassword;
    @FXML
    private Button btnHelpLogIn;

    private Parent root = null;

    private FXMLLoader loader = null;

    private Stage stage;

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getPackage() + "." + LoginController.class.getName());

    private Stage getStage() {
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
     * inicializa la ventana 
     * @param root elemento raiz de la ventana
     */
    public void initStage(Parent root) {
        LOGGER.info("Cargando ventana de inicio");
        Scene sceneLogin = new Scene(root);

        //The window starts
        stage = new Stage();
        stage.setScene(sceneLogin);
        stage.setResizable(false);

        // KEYCOMBINATIONS
        KeyCombination HelpKc = new KeyCodeCombination(KeyCode.H, KeyCombination.ALT_DOWN);
        btnHelpLogIn.setText("_HELP");
        KeyCombination loginKc = new KeyCodeCombination(KeyCode.L, KeyCombination.ALT_DOWN);
        btnLogIn.setText("_LOGIN");
        KeyCombination recoverKc = new KeyCodeCombination(KeyCode.F2);

        Mnemonic login = new Mnemonic(btnLogIn, loginKc);
        Mnemonic helpLogin = new Mnemonic(btnHelpLogIn, HelpKc);
        Mnemonic recoverPassword = new Mnemonic(hplRecoverPassword, recoverKc);

        sceneLogin.addMnemonic(login);
        sceneLogin.addMnemonic(helpLogin);
        sceneLogin.addMnemonic(recoverPassword);

        //Adding Accelerators to scene
        Runnable rLogin = () -> {
            try {
                loginAction();
            } catch (Exception ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        Runnable rHelp = () -> helpButtonAction();
        Runnable rRecover = () -> recoverPasswordAction();

        sceneLogin.getAccelerators().put(loginKc, rLogin);
        sceneLogin.getAccelerators().put(HelpKc, rHelp);
        sceneLogin.getAccelerators().put(recoverKc, rRecover);

        //PROMPTEXTS
        textFieldUsername.setPromptText("Inserte su nombre de usuario");
        textFieldUsername.setFocusTraversable(false);
        textFieldPassword.setPromptText("Inserte su contraseña");
        textFieldPassword.setFocusTraversable(false);

        // Tooltips config
        Tooltip usernameTT = new Tooltip("Inserte su nombre de usuario");
        textFieldUsername.setTooltip(usernameTT);
        Tooltip passwdTT = new Tooltip("Inserte su contraseña");
        textFieldPassword.setTooltip(passwdTT);
        Tooltip loginTT = new Tooltip("Pulse para hacer login");
        btnLogIn.setTooltip(loginTT);
        Tooltip helpTT = new Tooltip("Pulse para ayuda");
        btnHelpLogIn.setTooltip(helpTT);

        // *****************CONTROLS*************** 
        // LOGIN BUTTON
        btnLogIn.setOnAction((event) -> {
            try {
                loginAction();
            } catch (Exception ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        // RECOVER PASSWORD HYPERLINK
        hplRecoverPassword.setOnAction((event) -> {
            recoverPasswordAction();
        });

        // HELP BUTTON
        btnHelpLogIn.setOnAction((event) -> {
            helpButtonAction();
        });

        stage.setTitle("Login 2DAM2CURIOUS");
         LOGGER.info("Ventana de inicio cargada");
        stage.show();

        stage.setOnCloseRequest((WindowEvent event) -> {
             LOGGER.info("Cerrando ventana");
            // consume event
            event.consume();
            // show close dialog
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("¿Está seguro que desea salir de la aplicación?");
            alert.initOwner(stage);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Platform.exit();
                 LOGGER.info("Ventana de inicio cerrada");
            }
        });

    }

    //**************************M E T H O D S***********************************
    /**
     * Compara el login y contraseña con los datos del servidor
     * @throws Exception 
     */
    private void loginAction() throws Exception {
        UtilsWindows alert = new UtilsWindows();
        try {
            // Login Button Settings
            //It is verified that the fields cannot be empty
            if (textFieldPassword.getText().equals("") || textFieldUsername.getText().equals("")) {
                alert.alertWarning("Error", "Debe introducir el login y la contraseña.","");
            } else {// The user is built
                User usu = new User();
                UserClientService client = new UserClientService();
                //encriptamos
                EncryptionClientClass instance = new EncryptionClientClass();
                String encryptedPassword = null;
                encryptedPassword = instance.encrypt(textFieldPassword.getText());
                usu = client.login(User.class, textFieldUsername.getText(), encryptedPassword);
                //If the credentials are correct, if not go to exceptions
                LOGGER.info("Inicio correct, Cargando datos del usuario");
                LogOutController controller = new LogOutController();
                loader = new FXMLLoader(getClass().getResource("/fxmlWindows/GU03_LogOut.fxml"));
                root = (Parent) loader.load();
                controller = ((LogOutController) loader.getController());
                controller.initStage(root, usu);
                stage.close();
            }

        } catch (ProcessingException ex) {
            LOGGER.warning(ex.getMessage());
            alert.alertError("Error", "Hay un problema con la conexión, consulte con su empresa/entidad.","okButtonServerBad");
        } catch (NotAuthorizedException ex) {
            LOGGER.warning(ex.getMessage());
            alert.alertError("Error", "Login de usuario incorrecto.","okButtonLoginIncorrecto");
        } catch (NotFoundException ex) {
            LOGGER.warning(ex.getMessage());
            alert.alertInformation("Error", "Contraseña incorrecta.","okButtonPasswordIncorrecto");
        } catch (InternalServerErrorException ex) {
            LOGGER.warning(ex.getMessage());
            alert.alertWarning("Error", "Usuario no disponible, consulte con su empresa/entidad.","okButtonCamposVacios");
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
            alert.alertWarning("Error", "Error grave. Pongase en contacto con su empresa/entidad.","okButtonCamposVacios");
        }
    }
    /**
     * Accion del boton de ayuda de la ventana
     */
    private void helpButtonAction() {
        try {
            // Help button configuration
            LOGGER.info("Cargand ventana de ayuda.");
            loader = new FXMLLoader(getClass().getResource("/fxmlWindows/HelpLogin.fxml"));
            root = (Parent) loader.load();
            LoginHelpController controller = ((LoginHelpController) loader.getController());
            controller.initStage(root);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }
    /**
     * 
     * Accion del hiperlink para recuperar contraseña
     */
    private void recoverPasswordAction() {
        try {
            // Help button configuration
            LOGGER.info("Cargando ventana de recuperacion de contraseña.");
            loader = new FXMLLoader(getClass().getResource("/fxmlWindows/GU_RecoverPassword.fxml"));
            root = (Parent) loader.load();
            RecoverPasswordController controller = ((RecoverPasswordController) loader.getController());
            controller.initStage(root);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }

}