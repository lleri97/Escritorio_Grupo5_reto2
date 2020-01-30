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
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import servicesRestfull.DocumentClientService;
import utils.UtilsWindows;

/**
 * FXML Controller class
 *
 * @author Yeray
 */
public class NewDocumentController {

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

    /**
     * Initializes the controller class.
     */
    public void initStage(Parent root, Document doc, User user, String mod) {
        content = doc.getDocumentContent();
        this.mod = mod;
        this.user = user;
        if (doc.getName() != "") {
            textFielTittle.setText(doc.getName());
            textAreaDescription.setText(doc.getDescription());
            lblSelectedDoc.setText(doc.getName());
        }

        Scene sceneNewDocument = new Scene(root);
        stage = new Stage();
        stage.setScene(sceneNewDocument);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        btnAddDocument.setDisable(false);
        lblError.setVisible(false);
        lblSelectedDoc.setVisible(false);
        if (mod.equalsIgnoreCase("modify")) {
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

    }

    public void chooseFile() {
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
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void newDocumentAction() {
        UtilsWindows alert = new UtilsWindows();
        Document document = new Document();
        if (content == null) {
            alert.alertWarning("Aviso", "No hay ningun archivo cargado.","");
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
                    alert.alertInformation("Información", "Documento modificado con exito.","");

                } else {
                    documentClient.createNewDocument(document);
                    alert.alertInformation("Información", "Documento subido con exito.","");
                }
                stage.close();
            } catch (Exception e) {
                if (textAreaDescription.getText().length() == 0 || textFielTittle.getText().length() == 0) {
                    alert.alertWarning("Aviso", "Los campos no pueden estar vacíos","");
                } else {
                    alert.alertError("Error", "Error al subir el documento.","");
                    Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, e);
                }
            }

        }
    }

}