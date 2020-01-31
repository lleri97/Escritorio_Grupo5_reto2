/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.User;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import servicesRestfull.UserClientService;
import utils.UtilsWindows;

/**
 *
 * @author Fran
 */
public class RecoverPasswordController {

    @FXML
    private TextField TextFieldEmail;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnSend;
    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getPackage() + "." + LoginController.class.getName());

    /**
     * inicializando la ventana de recuperacion de contraseña
     * @param root 
     */
    public void initStage(Parent root) {
        LOGGER.info("Cargando ventana de recuperacion de contraseña");
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Recuperación de contraseña");
        stage.initModality(Modality.APPLICATION_MODAL);
        btnSend.setOnAction(this::handleButtonAction);
        btnBack.setOnAction(this::handleButtonAction);
        LOGGER.info("Ventana cargada");
        stage.show();
    }
    /**
     * Metodo que ralciona botones con acciones
     * @param event 
     */
    public void handleButtonAction(ActionEvent event) {
        if ((Button) event.getSource() == btnBack) {
            LOGGER.info("cerrando ventana de recuperacion de contraseña");
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.close();
        } else if ((Button) event.getSource() == btnSend) {
            LOGGER.info("Preparando para enviar contraseña");
            /**
             * Aqui ira el codigo para comprobar el email y mandar la contraseña
             */
            UtilsWindows alert = new UtilsWindows();
            try {
                User userEmail = new User();
                userEmail.setEmail(TextFieldEmail.getText());
                UserClientService client = new UserClientService();
                client.recoverPassword(userEmail, TextFieldEmail.getText());
                alert.alertInformation("Recuperación de contraseña", "Su nueva contraseña ha sido enviada a su email con éxito.", "btnEmailOk");
                LOGGER.info("Contraseña enviada con exito");
            } catch (InternalServerErrorException e) {
                alert.alertError("Error", "Fallo al enviar su nueva contraseña a su correo.", "btnEmailError");
            } catch (NotAuthorizedException e) {
                alert.alertWarning("Error", "El email introducido no existe.", "btnErrorEmailNotFound");
            }

        }
    }
}
