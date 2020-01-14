/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author 2dam
 */
public class SignUpController {
     private Stage stage;
     
      public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public void initStage(Parent root){
        
        Scene sceneSignUp = new Scene(root);
        stage = new Stage();
        stage.setScene(sceneSignUp);
        stage.setResizable(false);
        stage.show();
    }
}