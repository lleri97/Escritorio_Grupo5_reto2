/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.User;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
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
import servicesRestfull.UserClientService;

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

    private User usu;

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

    public void initStage(Parent root) {
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
        Runnable rLogin = () -> loginAction();
        Runnable rHelp = () -> helpButtonAction();
        Runnable rRecover = () -> recoverPasswordAction();

        sceneLogin.getAccelerators().put(loginKc, rLogin);
        sceneLogin.getAccelerators().put(HelpKc, rHelp);
        sceneLogin.getAccelerators().put(recoverKc, rRecover);

        //PROMPTEXTS
        textFieldUsername.setPromptText("Enter your username");
        textFieldUsername.setFocusTraversable(false);
        textFieldPassword.setPromptText("Enter your password");
        textFieldPassword.setFocusTraversable(false);

        // Tooltips config
        Tooltip usernameTT = new Tooltip("INSERT your user LOGIN");
        textFieldUsername.setTooltip(usernameTT);
        Tooltip passwdTT = new Tooltip("INSERT your user PASSWORD");
        textFieldPassword.setTooltip(passwdTT);
        Tooltip loginTT = new Tooltip("CLICK here for LOGIN");
        btnLogIn.setTooltip(loginTT);
        Tooltip helpTT = new Tooltip("CLICK here for HELP");
        btnHelpLogIn.setTooltip(helpTT);

        // *****************CONTROLS*************** 
        // LOGIN BUTTON
        btnLogIn.setOnAction((event) -> {
            loginAction();
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
        stage.show();

        stage.setOnCloseRequest((WindowEvent event) -> {
            // consume event
            event.consume();
            // show close dialog
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Close Confirmation");
            alert.setHeaderText("Do you really want to quit?");
            alert.initOwner(stage);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Platform.exit();
            }
        });

        LOGGER.info("Window loaded correctly.");
    }

    //**************************M E T H O D S***********************************
    private void loginAction() {
        try {
            // Login Button Settings
            //It is verified that the fields cannot be empty
            if (textFieldPassword.getText().equals("") || textFieldUsername.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Uncompleted data");
                Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setId("okbutton");
                alert.showAndWait();
            } else {// The user is built
                usu = new User();

                UserClientService client = new UserClientService();
               // usu = client.login(User.class, textFieldUsername.getText(), textFieldPassword.getText());
                
                //If the credentials are correct, if not go to exceptions
                LOGGER.info("Login made successfully. Loading user profile.");
                LogOutController controller = new LogOutController();
                loader = new FXMLLoader(getClass().getResource("/fxmlWindows/GU03_LogOut.fxml"));
                root = (Parent) loader.load();
                controller = ((LogOutController) loader.getController());
                controller.initStage(root);
            }

        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }

    private void helpButtonAction() {
        try {
            // Help button configuration
            LOGGER.info("Loading help window.");

            loader = new FXMLLoader(getClass().getResource("/fxmlWindows/HelpLogin.fxml"));
            root = (Parent) loader.load();
            LoginHelpController controller = ((LoginHelpController) loader.getController());
            controller.initStage(root);

        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }

    }

    private Runnable recoverPasswordAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
