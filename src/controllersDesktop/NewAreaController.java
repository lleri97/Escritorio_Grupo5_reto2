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
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import servicesRestfull.AreaClientService;
import servicesRestfull.DepartmentClientService;
import servicesRestfull.DocumentClientService;

/**
 * FXML Controller class
 *
 * @author Andoni
 */
public class NewAreaController {
    
    @FXML
    private BorderPane paneNewArea;
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
    private static final Logger LOGGER = Logger.getLogger(LogOutController.class.getPackage() + "." + LogOutController.class.getName());
    
    Stage stage;

    /**
     * Inicializa la clase controladora.
     */
    public void initStage(Parent root, User usuario, Area area, String mod) {
        LOGGER.info("Iniciando ventana de areas");
        if (mod == "modify") {
            LOGGER.info("Iniciando modificacion de areas");
            textFieldNameArea.setText(area.getName());
            if (area.getDocuments() != null) {
                comboDocument.getItems().addAll(area.getDocuments());
            }
            
            btnAddArea.setText("Confirmar");
            btnAddArea.setOnAction((value) -> {
                LOGGER.info("Enviando datos al servidor para modificacion");
                area.setName(textFieldNameArea.getText());
                Set<Document> docs = new HashSet<Document>();
                docs.add((Document) comboDocument.getSelectionModel().getSelectedItem());
                area.setDocuments(docs);
                AreaClientService areaService = new AreaClientService();
                areaService.edit(area);
                LOGGER.info("Modificacion en servidor correcta");
            });
        } else {
            btnAddArea.setOnAction((value) -> {
                Area createArea = new Area();
                createArea.setName(textFieldNameArea.getText());
                createArea.setDocuments((Set<Document>) comboDocument.getValue());
                AreaClientService areaService = new AreaClientService();
                areaService.create(createArea);
            });
            
        }
        
        this.usuario = usuario;
        
        Scene sceneNewArea = new Scene(root);
        stage = new Stage();
        stage.setScene(sceneNewArea);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        btnCancel.setOnAction((value) -> {
            stage.close();
        });
        
        cargarDocumentos(usuario.getPrivilege());
        
        stage.show();
        
    }

    /**
     * Cargara los documentos dependiendo del privilegio de usuario
     *
     * @param privilegio
     */
    private void cargarDocumentos(UserPrivilege privilegio) {
        documentService = new DocumentClientService();
        if (usuario.getPrivilege().equals(UserPrivilege.SUPERADMIN)) {
            LOGGER.info("Cargando todos los documentos");
            Set<Document> documents = new HashSet<Document>(); //llamar servidor y cargar entidades
            documents = documentService.findAll(new GenericType<Set<Document>>() {
            });
            comboDocument.getItems().clear();
            comboDocument.getItems().addAll(documents);
            
        } else if (usuario.getPrivilege().equals(UserPrivilege.COMPANYADMIN)) {
            LOGGER.info("Cargando documentos");
            comboDocument.getItems().clear();
            comboDocument.getItems().addAll(usuario.getDocuments());
        }
    }
    
}
