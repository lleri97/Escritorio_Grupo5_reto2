/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.Company;
import entitiesModels.User;
import entitiesModels.UserPrivilege;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import servicesRestfull.CompanyClientService;

/**
 *
 * @author Fran
 */
public class SignUpController {

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnCreateUser;
    @FXML
    private Button btnHelpSignUp;
    @FXML
    private Button btnAddPic;
    @FXML
    private ComboBox comboPrivilege;
    @FXML
    private ComboBox comboEntity;
    @FXML
    private TextField textLogin;
    @FXML
    private TextField textEmail;
    @FXML
    private TextField textFullName;
    @FXML
    private DatePicker dPickerNacimiento;// id.toEpochDay(); new Date(id.toEpochDay()); PARA LEER FECHA
    // de date a localdate --> instant= Instant.ofEpochMilli(date.getTime());    LocalDateTime.ofInstant(instant,);

    private static final Logger LOGGER = Logger.getLogger(SignUpController.class.getPackage() + "." + SignUpController.class.getName());

    private Stage stage;

    private boolean email = false;

    private User usuario;

    /**
     * Set a Stage in stage attribute
     *
     * @param stage The stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Method to initialize JavaFX windows
     *
     * @param root Parent will be used
     */
    void initStage(Parent root, User usuario) {
        Scene scene = new Scene(root);
        this.usuario = usuario;

        //The window starts
        stage = new Stage();

        // Keyboard shortcuts
        KeyCombination CreateUser = new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN);
        KeyCombination Cancel = new KeyCodeCombination(KeyCode.X, KeyCombination.ALT_DOWN);
        KeyCombination Help = new KeyCodeCombination(KeyCode.H, KeyCombination.ALT_DOWN);

        Mnemonic createUser = new Mnemonic(btnCreateUser, CreateUser);
        Mnemonic cancel = new Mnemonic(btnCancel, Cancel);
        Mnemonic help = new Mnemonic(btnHelpSignUp, Help);

        scene.addMnemonic(help);
        scene.addMnemonic(cancel);
        scene.addMnemonic(createUser);
        btnCreateUser.setDisable(true);

        // Tooltips config
        Tooltip backTT = new Tooltip("CANCEL SigningUp");
        btnCancel.setTooltip(backTT);
        Tooltip createTT = new Tooltip("CLICK here for SigningUp");
        btnCreateUser.setTooltip(createTT);
        Tooltip loginTT = new Tooltip("Max 40 characters");
        textLogin.setTooltip(loginTT);
        Tooltip nameTT = new Tooltip("Max 40 characters");
        textFullName.setTooltip(nameTT);
        Tooltip emailTT = new Tooltip("Max 40 characters Contains xxx");
        textEmail.setTooltip(emailTT);
        User usuPrueba = usuario;
        //cargar combos
        cargarPrivilegios(usuario.getPrivilege());
        cargarEntidades(usuario);

        // Actions
        btnCancel.setOnAction(this::handleButtonAction);
        btnHelpSignUp.setOnAction(this::handleButtonAction);
        btnCreateUser.setOnAction(this::handleButtonAction);
        textEmail.textProperty().addListener(this::handleTextChanged);
        textFullName.textProperty().addListener(this::handleTextChanged);
        textLogin.textProperty().addListener(this::handleTextChanged);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Nuevo Usuario 2DAM2CURIOUS");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void handleButtonAction(ActionEvent event) {

    }

    public void handleTextChanged(ObservableValue observable, String oldValue, String newValue) {
        // Field validators for the introduction of a new user
        if (textLogin.getText().trim().length() > 40) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("El login no puede tener más de 40 carácteres.");
            Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setId("okbutton");
            alert.showAndWait();
            btnCreateUser.setDisable(true);
        } else if (textEmail.getText().trim().length() > 40) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("El email no puede tener más de 40 carácteres.");
            Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setId("okbutton");
            alert.showAndWait();
        } else if (textFullName.getText().trim().length() > 40) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("El nombre completo no puede tener más de 40 carácteres.");
            Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setId("okbutton");
            alert.showAndWait();
        } else if (textLogin.getText().trim().length() >= 1
                && textEmail.getText().trim().length() >= 1
                && textFullName.getText().trim().length() >= 1
                && comboPrivilege.getSelectionModel().isEmpty()
                && comboEntity.getSelectionModel().isEmpty()) {
            btnCreateUser.setDisable(false);// Create user button is enabled
        } else {
            btnCreateUser.setDisable(true);// Create user button is disabled
        }
    }

    private void cargarEntidades(User usuario) {
        CompanyClientService clientCompany = new CompanyClientService();
        if (usuario.getPrivilege().equals(UserPrivilege.SUPERADMIN)) {
            //entities = clientCompany.findAll(new GenericType<List<Company>>()); //llamar servidor y cargar entidades
            List<Company> entities = Arrays.asList(usuario.getCompany()); 
            comboEntity = new ComboBox((ObservableList) entities);
            comboEntity.getSelectionModel().selectFirst();
            
        } else if (usuario.getPrivilege().equals(UserPrivilege.COMPANYADMIN)) {
            List<Company> entities = Arrays.asList(usuario.getCompany()); 
            comboEntity.getItems().clear();
            comboEntity.getItems().addAll(entities);
        }
    }

    private void cargarPrivilegios(UserPrivilege privilegio) {
        if (privilegio.equals(UserPrivilege.SUPERADMIN)) {
            List<UserPrivilege> privileges;
            privileges = Arrays.asList(UserPrivilege.USER, UserPrivilege.COMPANYADMIN, UserPrivilege.SUPERADMIN);
            comboPrivilege.getItems().clear();
            comboPrivilege.getItems().addAll(privileges);
        } else if (privilegio.equals(UserPrivilege.COMPANYADMIN)) {
            List<UserPrivilege> privileges;
            privileges = Arrays.asList(UserPrivilege.USER, UserPrivilege.COMPANYADMIN);
            comboPrivilege.getItems().clear();
            comboPrivilege.getItems().addAll(privileges);
        }

    }

}