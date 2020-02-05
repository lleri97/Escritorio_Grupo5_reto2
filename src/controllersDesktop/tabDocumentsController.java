/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.Document;
import entitiesModels.DocumentStatus;
import entitiesModels.User;
import entitiesModels.UserPrivilege;
import entitiesModels.UserStatus;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import servicesRestfull.DocumentClientService;
import servicesRestfull.UserClientService;

/**
 * FXML Controller class
 *
 * @author Yeray Andoni Fran Ruben
 */
public class tabDocumentsController {
    
    @FXML
    private AnchorPane AnchorPaneDoc;
    @FXML
    private Button btnNewDocument;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnDeleteDocument;
    @FXML
    private Button btnModifyDocument;
    @FXML
    private Button btnDownloadDocument;
    @FXML
    private TableView tableDocuments;
    @FXML
    private TableColumn columnName;
    @FXML
    private TableColumn columnDescription;
    @FXML
    private TableColumn columnStatus;
    @FXML
    private TableColumn columnDate;
    @FXML
    private CheckBox chkBoxDisabled;
    @FXML
    private CheckBox chkBoxEnabled;
    
    private User usuario;
    private Document doc;
    
    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger(SignUpController.class.getPackage() + "." + SignUpController.class.getName());
    
    private Set<Document> documentList;

    /**
     * Metodo de inicializacion de de ventanas fxml
     *
     * @param usuario objeto del tipo User
     * @param stage objeto que nos devuelve la escena
     */
    public void initStage(User usuario, Stage stage) {
        LOGGER.info("Inicializando panel de administracion de documentos");
        stage = new Stage();
        this.stage = stage;
        this.usuario = usuario;
        chkBoxEnabled.setSelected(true);
        if (usuario.getPrivilege() == UserPrivilege.USER) {
            chkBoxDisabled.setVisible(false);
            chkBoxEnabled.setVisible(false);
        }
        btnDeleteDocument.setVisible(false);
        btnModifyDocument.setVisible(false);
        btnDownloadDocument.setVisible(false);
        
        btnNewDocument.setOnAction((event) -> {
            lanzarNewDocumentWindow();
        });
        btnSearch.setOnAction((event) -> {
            insertData();
        });
        
        btnDeleteDocument.setOnAction(this::handleButtonAction);
        btnModifyDocument.setOnAction(this::handleButtonAction);
        btnDownloadDocument.setOnAction(this::handleButtonAction);
        tableDocuments.getSelectionModel().selectedItemProperty().addListener(this::handleUsersTabSelectionChanged);
        LOGGER.info("Panel inicializado con exito");
    }
    /***
     * Metodo que lanza la ventana de nuevo documento
     */
    public void lanzarNewDocumentWindow() {
        try {
            LOGGER.info("Iniciando lanzamieto de ventana de nuevo documento");
            NewDocumentController controller = new NewDocumentController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/GU_NewDocument.fxml"));
            Parent root = (Parent) loader.load();
            controller = ((NewDocumentController) loader.getController());
            doc = new Document();
            doc.setName("");
            controller.initStage(root, doc, usuario, "");
        } catch (IOException ex) {
        }
    }
    /**
     * Metodo para añadir datos a la tabla
     */
    public void insertData() {
        LOGGER.info("Preparando los datos para insertarlos en la tabla");
        columnName.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        columnDescription.setCellValueFactory(
                new PropertyValueFactory<>("description"));
        columnDate.setCellValueFactory(
                new PropertyValueFactory<>("uploadDate"));
        columnStatus.setCellValueFactory(
                new PropertyValueFactory<>("status"));
        
        DocumentClientService documentService = new DocumentClientService();
        documentList = new HashSet<Document>();
        documentList = documentService.findAll(new GenericType<Set<Document>>() {
        });
 
        if (chkBoxDisabled.isSelected() && chkBoxEnabled.isSelected()) {
            chargeAllDocs();
        } else if (chkBoxDisabled.isSelected()) {
            chargeDisabledDocs();
        } else if (chkBoxEnabled.isSelected()) {
            chargeEnabledUsers();
        } else {
            chargeAllDocs();
        }
        LOGGER.info("Datos cargados en la tabla con exito");
    }
    /**
     * metodo que relacion la tabla con acciones
     * @param observable tipo ObservableValue
     * @param olsValue tipo Object
     * @param newValue tipo Object
     */
    public void handleUsersTabSelectionChanged(ObservableValue observable, Object olsValue, Object newValue) {
        btnDeleteDocument.setVisible(true);
        btnModifyDocument.setVisible(true);
        btnDownloadDocument.setVisible(true);
    }
    /**
     * Metodo que relaciona botones con acciones
     * @param event tipo Event
     */
    public void handleButtonAction(ActionEvent event) {
        if ((Button) event.getSource() == btnModifyDocument) {
            LOGGER.info("Preparando para modificar datos");
            try {
                NewDocumentController controller = new NewDocumentController();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/GU_NewDocument.fxml"));
                Parent root = (Parent) loader.load();
                controller = ((NewDocumentController) loader.getController());
                doc = new Document();
                doc = (Document) tableDocuments.getSelectionModel().getSelectedItem();
                controller.initStage(root, doc, usuario, "modify");
            } catch (IOException ex) {
                Logger.getLogger(tabUsersController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else if ((Button) event.getSource() == btnDeleteDocument) {
            try {
                LOGGER.info("Preparando para borrar");
                DocumentClientService documentService = new DocumentClientService();
                Document deleteDoc = new Document();
                deleteDoc = (Document) tableDocuments.getSelectionModel().getSelectedItem();
                documentService.remove(deleteDoc.getId());
                LOGGER.info("Datos borrados recargando la tabla");
                insertData();
                
            } catch (Exception e) {
                Logger.getLogger(tabUsersController.class.getName()).log(Level.SEVERE, null, e);
            }
        } else if ((Button) event.getSource() == btnDownloadDocument) {
            try {
                LOGGER.info("Preparando descarga del documento");
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
                fileChooser.getExtensionFilters().add(extensionFilter);
                File downloadFile = fileChooser.showSaveDialog(stage);
                doc = new Document();
                doc = (Document) tableDocuments.getSelectionModel().getSelectedItem();
                bytesToFile(downloadFile, doc.getDocumentContent());
                LOGGER.info("Documento descargado con exito");
            } catch (Exception e) {
                Logger.getLogger(tabUsersController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    /***
     * Metodo que carga los documentos en la tabla
     */
    public void chargeAllDocs() {
        LOGGER.info("Cargando documentos");
        List<Document> list = new ArrayList<Document>(documentList);
        ObservableList<Document> docList = FXCollections.observableArrayList(list);
        tableDocuments.setItems(docList);
        
    }
    /**
     * Metodo que carga los documentos desactivados
     */
    private void chargeDisabledDocs() {
        LOGGER.info("Cargando documentos deshabilitados");
        if (usuario.getPrivilege() == UserPrivilege.SUPERADMIN || usuario.getPrivilege() == UserPrivilege.COMPANYADMIN) {
            List<Document> list = new ArrayList<Document>(documentList);
            ObservableList<Document> docList = FXCollections.observableArrayList();
            
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getStatus() == DocumentStatus.DISABLED) {
                    docList.add(list.get(i));
                }
            }
            tableDocuments.setItems(docList);
            
        }
    }
    /**
     * Metodo para cargar usuarios activos
     */
    private void chargeEnabledUsers() {
        LOGGER.info("Cargando documentos habilitados");
        if (usuario.getPrivilege() == UserPrivilege.SUPERADMIN || usuario.getPrivilege() == UserPrivilege.COMPANYADMIN) {
            List<Document> list = new ArrayList<Document>(documentList);
            ObservableList<Document> docList = FXCollections.observableArrayList();
            
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getStatus() == DocumentStatus.ENABLED) {
                    docList.add(list.get(i));
                }
            }
            tableDocuments.setItems(docList);
            
        }
    }
    /**
     * Metodo que transforma los byte array a file
     * @param downloadFile
     * @param documentContent 
     */
    private void bytesToFile(File downloadFile, byte[] documentContent) {
        FileOutputStream fos = null;
        try {
            LOGGER.info("Cargando fichero");
            fos = new FileOutputStream(downloadFile.getPath());
            fos.write(documentContent);
            LOGGER.info("Fichero cargado con exito");
        } catch (Exception e) {
            Logger.getLogger(tabUsersController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    Logger.getLogger(tabUsersController.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    }
}
