/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.Area;
import entitiesModels.Document;
import entitiesModels.User;
import entitiesModels.UserPrivilege;
import java.util.HashSet;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import servicesRestfull.AreaClientService;
import servicesRestfull.DepartmentClientService;
import servicesRestfull.DocumentClientService;

/**
 * FXML Controller class
 *
 * @author Yeray
 */
public class NewAreaController {

    @FXML
    private Label lblNewArea;
    @FXML
    private TextField textFieldNameArea;
    @FXML
    private Label lblName;
    @FXML
    private Label lblError;
    @FXML
    private Label lblCompany;
    @FXML
    private Label lblDepartment;
    @FXML
    private Button btnAddArea;
    @FXML
    private Button btnCancel;
    @FXML
    private ComboBox comboDocument;

    private User usuario;

    private DocumentClientService documentService;
    private DepartmentClientService departService;

    Stage stage;

    /**
     * Initializes the controller class.
     */
    public void initStage(Parent root, User usuario) {

        this.usuario = usuario;

        Scene sceneNewArea = new Scene(root);
        stage = new Stage();
        stage.setScene(sceneNewArea);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        btnCancel.setOnAction((value) -> {
            stage.close();
        });
        btnAddArea.setOnAction((value) -> {
            Area area = new Area();
            area.setName(textFieldNameArea.getText());
            area.setDocuments((Set<Document>) comboDocument.getValue());
            AreaClientService areaService = new AreaClientService();
            areaService.create(area);
        });
        cargarDocumentos(usuario.getPrivilege());


        stage.show();

    }

    private void cargarDocumentos(UserPrivilege privilegio) {
        documentService = new DocumentClientService();
        if (usuario.getPrivilege().equals(UserPrivilege.SUPERADMIN)) {
            Set<Document> documents = new HashSet<Document>(); //llamar servidor y cargar entidades
            documents = documentService.findAll(new GenericType<Set<Document>>() {
            });
            comboDocument.getItems().clear();
            comboDocument.getItems().addAll(documents);
          

        } else if (usuario.getPrivilege().equals(UserPrivilege.COMPANYADMIN)) {
          
            comboDocument.getItems().clear();
            comboDocument.getItems().addAll(usuario.getDocuments());
        }
    }


}
