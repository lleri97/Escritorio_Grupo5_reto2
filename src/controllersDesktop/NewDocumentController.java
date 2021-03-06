/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.Document;
import entitiesModels.DocumentStatus;
import entitiesModels.User;
import java.io.File;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import servicesRestfull.DocumentClientService;
import utils.UtilsWindows;

/**
 * FXML Controller class
 *
 * @author Yeray Ruben Andoni
 */
public class NewDocumentController {
    
    @FXML
    private BorderPane paneNewDoc;
    @FXML
    private Label lblNewDocument;
    @FXML
    private TextField textFielTittle;
    @FXML
    private TextArea textAreaDescription;
    @FXML
    private Label lblTittle;
    @FXML
    private Hyperlink hyperlinkDocument;
    @FXML
    private Label lblDescription;
    @FXML
    private Label lblError;
    @FXML
    private Label lblSelectedDoc;
    @FXML
    private Button btnAddDocument;
    @FXML
    private Button btnCancel;
    
    private Stage stage;
    
    private byte[] content;
    
    private File file;
    
    private User user;
    
    private String mod;
    
    private static final Logger LOGGER = Logger.getLogger(LogOutController.class.getPackage() + "." + LogOutController.class.getName());

    /**
     * Metodo que inicializa la ventana de NewDocument
     * @param root elemento raiz
     * @param doc objeto del tipo Document
     * @param user objeto del tipo User
     * @param mod tipo String
     */
    public void initStage(Parent root, Document doc, User user, String mod) {
        LOGGER.info("Inicializando la clase controladora");
        content = doc.getDocumentContent();
        this.mod = mod;
        this.user = user;
        Scene sceneNewDocument = new Scene(root);
        stage = new Stage();
        stage.setScene(sceneNewDocument);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        btnAddDocument.setDisable(false);
        lblError.setVisible(false);
        lblSelectedDoc.setVisible(false);
        if (mod.equalsIgnoreCase("modify")) {
            LOGGER.info("Cargando datos para modificar");
            btnAddDocument.setText("Modicar");
            lblNewDocument.setText("Modificar documento.");
            hyperlinkDocument.setText("Pulse para cambiar el contenido del documento.");
        }
        btnCancel.setOnAction((event) -> {
            stage.close();
        });
        
        btnAddDocument.setOnAction((event) -> {
            newDocumentAction();
        });
        hyperlinkDocument.setOnAction((event) -> {
            chooseFile();
            
        });
        
        stage.show();
        LOGGER.info("Ventana cargada con exito");
    }
    /**
     * Metodo para seleccionar el archivo para subir a la base de datos
     */
    public void chooseFile() {
         UtilsWindows alert = new UtilsWindows();
        LOGGER.info("Inicializando selector de archivos");
        FileChooser fc = new FileChooser();
        Document document = new Document();
        fc.setTitle("Buscar foto:");
        fc.getExtensionFilters().add(new ExtensionFilter("PDF files (*.pdf)", "*.PDF", "*.pdf"));
        
        file = fc.showOpenDialog(null);
        if (file != null) {
            lblSelectedDoc.setVisible(true);
            lblSelectedDoc.setText("Selected File: " + file.getAbsolutePath());
        }
        try {
            content = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            document = new Document();
            document.setDocumentContent(content);
        } catch (Exception ex) {
            alert.alertError("No hay documento", "Documento no seleccionado", "okButtonDocNull");
        }
    }
    /**
     * Metodo de nuevo documento
     */
    private void newDocumentAction() {
        UtilsWindows alert = new UtilsWindows();
        Document document = new Document();
        if (content == null) {
            alert.alertWarning("Aviso", "No hay ningun archivo cargado.", "okButtonDocNull");
        } else {
            try {
                if (textAreaDescription.getText().length() == 0 || textFielTittle.getText().length() == 0) {
                    throw new Exception();
                }
                DocumentClientService documentClient = new DocumentClientService();
                document.setDescription(textAreaDescription.getText());
                document.setName(textFielTittle.getText());
                document.setDocumentContent(content);
                document.setStatus(DocumentStatus.ENABLED);
                document.setVisibility(Boolean.TRUE);
                if (mod.equalsIgnoreCase("modify")) {
                    documentClient.updateDocument(document);
                    alert.alertInformation("Información", "Documento modificado con exito.", "okButton");
                    
                } else {
                    documentClient.createNewDocument(document);
                    alert.alertInformation("Información", "Documento subido con exito.", "okButton");
                }
                stage.close();
            } catch (Exception e) {
                if (textAreaDescription.getText().length() == 0 || textFielTittle.getText().length() == 0) {
                    alert.alertWarning("Aviso", "Los campos no pueden estar vacíos", "okButtonNull");
                } else {
                    alert.alertError("Error", "Error al subir el documento.", "okButtonError");
                    Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            
        }
    }
    
}
