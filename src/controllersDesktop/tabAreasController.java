/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
    
    
    public void inicializar(Pane contentPane){
        try {
            FXMLLoader loader = new FXMLLoader();
            AnchorPane pane = loader.load(getClass().getResource("/fxmlWindows/tabAreas.fxml"));
            contentPane.getChildren().setAll(pane);
           
        } catch (IOException ex) {
            Logger.getLogger(tabUsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       public void lanzarNewUserWindow() {
        
            try {
                NewAreaController controller = new NewAreaController();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/GU_NewArea.fxml"));
                Parent root = (Parent) loader.load();
                controller = ((NewAreaController) loader.getController());
                controller.initStage(root);
            } catch (IOException ex) {
                Logger.getLogger(tabUsersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

   
    
}
