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
import java.sql.Blob;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.sql.rowset.serial.SerialBlob;
import servicesRestfull.DocumentClientService;

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

    private Document doc;

    private byte[] content;

    private File file;

    DocumentClientService documentClient;

    /**
     * Initializes the controller class.
     */
    public void initStage(Parent root, Document doc,User user) {
        
        if(doc.getName()!=""){
            textFielTittle.setText(doc.getName());
            textAreaDescription.setText(doc.getDescription());
            lblSelectedDoc.setText(doc.getName());
            btnAddDocument.setOnAction((event)->{
            documentClient.updateDocument(doc);
            });
        }else{
               btnAddDocument.setOnAction((event) -> {
            if (file == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No hay ningun archivo cargado");
                Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setId("okbutton");
                alert.showAndWait();
            } else {
                doc.setDescription(textAreaDescription.getText());
                doc.setName(textFielTittle.getText());
                doc.setStatus(DocumentStatus.ENABLED);
                doc.setVisibility(Boolean.TRUE);
                doc.setUser(user);
                documentClient.createNewDocument(doc);
            }
        });
        }
        
        Scene sceneNewDocument = new Scene(root);
        stage = new Stage();
        stage.setScene(sceneNewDocument);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        btnAddDocument.setDisable(true);
        lblError.setVisible(false);
        lblSelectedDoc.setVisible(false);
        btnCancel.setOnAction((event) -> {
            stage.close();
        });
     
        hyperlinkDocument.setOnAction((event) -> {
            chooseFile();

        });

        textAreaDescription.textProperty().addListener(this::handleTextChanged);
        textFielTittle.textProperty().addListener(this::handleTextChanged);

        stage.show();

    }

    public void chooseFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Buscar foto:");
        fc.getExtensionFilters().add(new ExtensionFilter("PDF files (*.pdf)", "*.PDF", "*.pdf"));

        file = fc.showOpenDialog(null);
        if (file != null) {
            lblSelectedDoc.setVisible(true);
            lblSelectedDoc.setText("Selected File: " + file.getAbsolutePath());
        }
        try {
            content = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            doc = new Document();
            doc.setDocumentContent(content);
        } catch (Exception ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleTextChanged(ObservableValue observable, String oldValue, String newValue) {

        if (textAreaDescription.getText().length() > 0 || textFielTittle.getText().length() > 0) {
            btnAddDocument.setDisable(false);
        }

    }

}
