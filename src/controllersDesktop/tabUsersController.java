/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.User;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private TableView tableUsers;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn emailColumn;
    @FXML
    private TableColumn companyColumn;
    @FXML
    private TableColumn lastAccessColumn;
    private ObservableList<User> usersData;

    public void inicializar(Pane contentPane){
        try {
            FXMLLoader loader = new FXMLLoader();
            AnchorPane pane = loader.load(getClass().getResource("/fxmlWindows/tabUsers.fxml"));
            contentPane.getChildren().setAll(pane);
           
        } catch (IOException ex) {
            Logger.getLogger(tabUsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    public void insertData() {
        
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("Nombre"));
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<>("Email"));
        companyColumn.setCellValueFactory(
                new PropertyValueFactory<>("Compañia"));
        lastAccessColumn.setCellValueFactory(
                new PropertyValueFactory<>("Ultimo acceso"));

        // tableUsers.setItems();
    }
        public void lanzarNewUserWindow() {
        
            try {
                SignUpController controller = new SignUpController();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/GU02_SignUp.fxml"));
                Parent root = (Parent) loader.load();
                controller = ((SignUpController) loader.getController());
                controller.initStage(root);
            } catch (IOException ex) {
                Logger.getLogger(tabUsersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
}
