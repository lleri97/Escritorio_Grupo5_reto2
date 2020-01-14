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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
private ObservableList <User> usersData;

public void insertData(){
   
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
}
