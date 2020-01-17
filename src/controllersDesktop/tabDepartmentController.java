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
public class tabDepartmentController {
    
    @FXML
    private Button btnNewDepartment;
    
    User usuario;
    
    

   public void inicializar(User usuario){
        this.usuario=usuario;
        
        btnNewDepartment.setOnAction((event) -> {
            lanzarNewDepartmentWindow();
        });
        
    }
    
            public void lanzarNewDepartmentWindow() {
        
            try {
                NewDepartmentController controller = new NewDepartmentController();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/GU_NewDepartment.fxml"));
                Parent root = (Parent) loader.load();
                controller = ((NewDepartmentController) loader.getController());
                controller.initStage(root, usuario);
            } catch (IOException ex) {
            }
        }

}
