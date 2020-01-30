/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.Company;
import entitiesModels.User;
import entitiesModels.UserPrivilege;
import entitiesModels.UserStatus;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.GenericType;
import servicesRestfull.CompanyClientService;
import servicesRestfull.UserClientService;
import utils.EncryptionClientClass;
import utils.UtilsWindows;
import utils.Validator;

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
    private Label labSingleFile;
    @FXML
    private TextField textPassword;
    @FXML
    private TextField textConfirmPassword;
    @FXML
    private CheckBox checkbStatus;
    @FXML
    private Label lbltitle_id;
    @FXML
    private DatePicker dPickerNacimiento;

    private static final Logger LOGGER = Logger.getLogger(SignUpController.class.getPackage() + "." + SignUpController.class.getName());

    private Stage stage;

    private boolean dataOK = false;

    private File photo;

    private byte[] photoContent;

    private byte[] photoContentDB;

    private User usuario;

    private boolean fechaVacia = false;

    private String mod;

    private UtilsWindows alert = new UtilsWindows();

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
    void initStage(Parent root, User usuario, String mod) {
        this.usuario = usuario;
        this.mod = mod;
        Scene scene = new Scene(root);
        if (mod.equalsIgnoreCase("modify")) {
            lbltitle_id.setText("Modificar perfil");
            textEmail.setText(usuario.getEmail());
            textFullName.setText(usuario.getFullname());
            textLogin.setText(usuario.getLogin());
            comboPrivilege.setValue(usuario.getPrivilege());
            textPassword.setText("");
            textConfirmPassword.setText("");
            if (usuario.getStatus().equals(UserStatus.ENABLED)) {
                checkbStatus.setSelected(true);
            } else {
                checkbStatus.setSelected(false);
            }
            if (usuario.getbDate() != null) {
                dPickerNacimiento.setValue(Instant.ofEpochMilli(usuario.getbDate().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
            }
            if (!usuario.getPrivilege().equals(UserPrivilege.SUPERADMIN)) {
                if (usuario.getPrivilege().equals(UserPrivilege.USER)) {
                    comboPrivilege.setDisable(true);
                }
                comboEntity.setValue(usuario.getCompany().getName());
                comboEntity.setDisable(true);
            }
            btnCreateUser.setText("Guardar");
        } else {
            lbltitle_id.setText("Nuevo usuario");
            textPassword.setVisible(false);
            textConfirmPassword.setVisible(false);
            checkbStatus.setSelected(true);
        }
        photoContentDB = usuario.getPhoto();

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
        btnAddPic.setOnAction(this::addPicAction);

        stage.setScene(scene);
        stage.setResizable(false);
        if (mod.equalsIgnoreCase("modify")) {
            stage.setTitle("Modificar Perfil");
        } else {
            stage.setTitle("Nuevo Usuario");
        }

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * Añadir foto perfil
     */
    private void addPicAction(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Buscar foto:");
        fc.getExtensionFilters().add(new ExtensionFilter("Img files", "*.png"));

        photo = fc.showOpenDialog(null);
        if (photo != null) {
            labSingleFile.setText("Selected File: " + photo.getAbsolutePath());
        }
        try {
            photoContent = Files.readAllBytes(Paths.get(photo.getAbsolutePath()));
        } catch (IOException ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (photoContent == null) {
                photoContent = photoContentDB;
            }
        }
    }

    private void cargarEntidades(User usuario) {
        CompanyClientService clientCompany = new CompanyClientService();
        if (usuario.getPrivilege().equals(UserPrivilege.SUPERADMIN)) {
            Set<Company> entities = new HashSet<Company>(); //llamar servidor y cargar entidades
            entities = clientCompany.findAll(new GenericType<Set<Company>>() {
            });
            comboEntity.getItems().clear();
            comboEntity.getItems().addAll(entities);

        } else if (usuario.getPrivilege().equals(UserPrivilege.COMPANYADMIN)) {
            Company entities = usuario.getCompany();
            comboEntity.getItems().clear();
            comboEntity.getItems().addAll(entities.getName());
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

    public void handleButtonAction(ActionEvent event) {
        UserClientService client = new UserClientService();
        User usu = new User();
        if ((Button) event.getSource() == btnCancel) {
            //Closes the Stage
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        } else if ((Button) event.getSource() == btnCreateUser) {
            if (textPassword.getText().equals(textConfirmPassword.getText())) {
                usu.setLogin(textLogin.getText());
                usu.setEmail(textEmail.getText());
                usu.setFullname(textFullName.getText());
                usu.setPhoto(photoContent);
                usu.setPassword(textPassword.getText());
                usu.setLastAccess(usuario.getLastAccess());
                if (!usuario.getPrivilege().equals(UserPrivilege.SUPERADMIN)) {
                    usu.setCompany((Company) usuario.getCompany());
                } else {
                    usu.setCompany((Company) comboEntity.getValue());
                }
                usu.setPrivilege((UserPrivilege) comboPrivilege.getValue());
                if (checkbStatus.isSelected()) {
                    usu.setStatus(UserStatus.ENABLED);
                } else {
                    usu.setStatus(UserStatus.DISABLED);
                }

                try {
                    LocalDate localDate = dPickerNacimiento.getValue();
                    Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                    usu.setbDate(Date.from(instant));
                } catch (Exception e) {
                    fechaVacia = true;
                } finally {
                    dataOK = dataValidator(usu);
                    if (dataOK) {
                        String alertTitle = "";
                        String alertContentText = "";
                        //encriptamos
                        String encryptedPass = null;
                        try {
                            encryptedPass = EncryptionClientClass.encrypt(textPassword.getText());
                        } catch (Exception ex) {
                            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        usu.setPassword(encryptedPass);
                        if (mod != "modify") {//crear
                            try {
                                client.create(usu);
                                textLogin.clear();
                                textEmail.clear();
                                textFullName.clear();
                                dPickerNacimiento.setValue(LocalDate.now());
                                LOGGER.info("Sign Up made successfully. Loading user profile.");
                                alertTitle = "Nuevo usuario";
                                alertContentText = "Nuevo usuario registrado correctamente.";
                                stage.close();
                            } catch (NotAuthorizedException e) {
                                LOGGER.warning(e.getMessage());
                                alert.alertError("Error", "Login y/o email ya existen en la base de datos.","");
                            } catch (NotFoundException e) {
                                LOGGER.warning(e.getMessage());
                                alert.alertError("Error", "Error al enviar su nueva contraseña a su correo. Intentelo más tarde.","");
                            } catch (InternalServerErrorException e) {
                                LOGGER.warning(e.getMessage());
                                alert.alertError("Error", "Error al dar de alta al usuario.","");
                            }
                        } else {//modificar
                            try {
                                usu.setId(usuario.getId());
                                client.edit(usu);
                                LOGGER.info("Update made successfully. Loading user profile.");
                                alertTitle = "Modificar perfil.";
                                alertContentText = "Los datos del perfil han sido modificados correctamente..";
                            } catch (InternalServerErrorException e) {
                                LOGGER.warning(e.getMessage());
                                alert.alertError("Error", "Login y/o email ya existen en la base de datos.","");
                            }

                        }
                        // An alert is issued giving a message that the operation has been a success
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle(alertTitle);
                        alert.setContentText(alertContentText);
                        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                        okButton.setId("okbutton");
                        alert.showAndWait();
                    }
                }
            } else {
                alert.alertWarning("Error", "Las contraseñas no coinciden.","");
            }

        }
    }

    private boolean dataValidator(User user) {
        boolean ret = true;
        String stringErrorData = "";
        if (textLogin.getText().trim().length() > 40 || textLogin.getText().trim().length() < 1) {
            stringErrorData = stringErrorData + "-El login no puede tener más de 40 carácteres y tiene que tener datos.";
            ret = false;
        }
        if (textEmail.getText().trim().length() > 40 || textEmail.getText().trim().length() < 1 || !Validator.emailChecker(user.getEmail())) {
            stringErrorData = stringErrorData + "\n-El email no puede tener más de 40 carácteres, tiene que tener datos y un formato correcto.";
            ret = false;
        }
        if (textFullName.getText().trim().length() > 40 || textFullName.getText().trim().length() < 1) {
            stringErrorData = stringErrorData + "\n-El nombre completo no puede tener más de 40 carácteres y tiene que tener datos.";
            ret = false;
        }
        if (comboPrivilege.getSelectionModel().isEmpty() || (comboEntity.getSelectionModel().isEmpty() && !usuario.getPrivilege().equals(UserPrivilege.SUPERADMIN))) {
            stringErrorData = stringErrorData + "\n-Debe seleccionar privilegio y empresa/entidad.";
            ret = false;
        }
        if (fechaVacia && !usuario.getPrivilege().equals(UserPrivilege.SUPERADMIN)) {
            stringErrorData = stringErrorData + "\n-Debe seleccionar una fecha de nacimiento.";
            ret = false;
        }
        if (!Validator.passwordChecker(textPassword.getText())) {
            stringErrorData = stringErrorData + "\n-La contraseña debe tener al menos minusculas, mayusculas, números y entre 8 y 40 caracteres.";
            ret = false;
        }
        if (!ret) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText(stringErrorData);
            Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setId("okbutton");
            alert.showAndWait();
        }
        return ret;
    }

}