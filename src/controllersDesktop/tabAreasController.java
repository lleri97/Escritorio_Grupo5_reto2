/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.User;
import entitiesModels.UserPrivilege;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
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
public class tabAreasController  {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btnNewArea;
    
    
    private User usuario;
    
    
    public void inicializar(User usuario){
        this.usuario=usuario;
        if(usuario.getPrivilege()==UserPrivilege.USER){
            btnNewArea.setDisable(true);
            btnNewArea.setVisible(false);
        }
        
        btnNewArea.setOnAction((event) -> {
            lanzarNewAreaWindow();
        });
        
    }
       public void lanzarNewAreaWindow() {
        
            try {
                NewAreaController controller = new NewAreaController();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/GU_NewArea.fxml"));
                Parent root = (Parent) loader.load();
                controller = ((NewAreaController) loader.getController());
                controller.initStage(root, usuario);
            } catch (IOException ex) {
            }
        }

   
    
}
