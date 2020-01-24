/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.Company;
import entitiesModels.User;
import entitiesModels.UserPrivilege;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import servicesRestfull.CompanyClientService;

/**
 * FXML Controller class
 *
 * @author Yeray
 */
public class tabEntityController {

    @FXML
    private Button btnNewCompany;
    @FXML
    private Button btnSearchEntity;
    @FXML
    private Label lblSearchEntity;

    
    private CompanyClientService companyClient;

    private User usuario;

    public void inicializar(User usuario) {
        this.usuario = usuario;

        if (usuario.getPrivilege() == UserPrivilege.USER || usuario.getPrivilege()==UserPrivilege.COMPANYADMIN) {
            btnNewCompany.setDisable(true);
            btnNewCompany.setVisible(false);
            btnSearchEntity.setDisable(true);
            btnSearchEntity.setVisible(false);
            lblSearchEntity.setVisible(false);
        } 

        btnNewCompany.setOnAction((event) -> {
            lanzarNewEntityWindow();
        });
        btnSearchEntity.setOnAction((event) -> {
           
        });

    }

    public void lanzarNewEntityWindow() {

        try {
            NewCompanyController controller = new NewCompanyController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/GU_NewCompany.fxml"));
            Parent root = (Parent) loader.load();
            controller = ((NewCompanyController) loader.getController());
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger("Esto peta aqui");
        }
    }

}
