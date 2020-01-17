/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Yeray
 */
public class tabDocumentsController  {
    
    
    @FXML
    private Button btnNewDocument;
    private User usuario;

     public void inicializar(User usuario){
        this.usuario=usuario;
        
        btnNewDocument.setOnAction((event) -> {
            lanzarNewDocumentWindow();
        });
        
    }
   
    public void lanzarNewDocumentWindow(){
    try {
                NewDocumentController controller = new NewDocumentController();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/GU_NewDocument.fxml"));
                Parent root = (Parent) loader.load();
                controller = ((NewDocumentController) loader.getController());
                controller.initStage(root);
            } catch (IOException ex) {
            }
        }
}

