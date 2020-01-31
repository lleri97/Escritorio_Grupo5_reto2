/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.User;
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
 * @author Yeray
 */
public class RecoverPasswordController {

    @FXML
    private TextField TextFieldEmail;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnSend;

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Recuperación de contraseña");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        btnSend.setOnAction(this::handleButtonAction);
        btnBack.setOnAction(this::handleButtonAction);
    }

    public void handleButtonAction(ActionEvent event) {
        if ((Button) event.getSource() == btnBack) {
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.close();
        } else if ((Button) event.getSource() == btnSend) {
            /**
             * Aqui ira el codigo para comprobar el email y mandar la contraseña
             */
            UtilsWindows alert = new UtilsWindows();
            try {
                User userEmail = new User();
                userEmail.setEmail(TextFieldEmail.getText());
                UserClientService client = new UserClientService();
                client.recoverPassword(userEmail, TextFieldEmail.getText());
                alert.alertInformation("Recuperación de contraseña", "Su nueva contraseña ha sido enviada a su email con éxito.","btnEmailOk");
            } catch (InternalServerErrorException e) {
                alert.alertError("Error", "Fallo al enviar su nueva contraseña a su correo.","btnEmailError");
            } catch (NotAuthorizedException e) {
                alert.alertWarning("Error", "El email introducido no existe.","btnErrorEmailNotFound");
            }

        }
    }
}