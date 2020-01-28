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
import javax.ws.rs.core.GenericType;
import servicesRestfull.DocumentClientService;
import servicesRestfull.UserClientService;

/**
 * FXML Controller class
 *
 * @author Yeray
 */
public class tabDocumentsController {

    @FXML
    private Button btnNewDocument;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnDeleteDocument;
    @FXML
    private Button btnModifyDocument;
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

    private Set<Document> documentList;

    public void initStage(User usuario) {
        this.usuario = usuario;
        if (usuario.getPrivilege() == UserPrivilege.USER) {
            chkBoxDisabled.setVisible(false);
            chkBoxEnabled.setVisible(false);
        }
        btnDeleteDocument.setVisible(false);
        btnModifyDocument.setVisible(false);

        btnNewDocument.setOnAction((event) -> {
            lanzarNewDocumentWindow();
        });
        btnSearch.setOnAction((event) -> {
            insertData();
        });

        btnDeleteDocument.setOnAction(this::handleButtonAction);
        btnModifyDocument.setOnAction(this::handleButtonAction);
        tableDocuments.getSelectionModel().selectedItemProperty().addListener(this::handleUsersTabSelectionChanged);

    }

    public void lanzarNewDocumentWindow() {
        try {
            NewDocumentController controller = new NewDocumentController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/GU_NewDocument.fxml"));
            Parent root = (Parent) loader.load();
            controller = ((NewDocumentController) loader.getController());
            doc = new Document();
            doc.setName("");
            controller.initStage(root, doc, usuario);
        } catch (IOException ex) {
        }
    }

    public void insertData() {

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
        }else if(chkBoxEnabled.isSelected()){
            chargeEnabledUsers();
    }else{
            chargeAllDocs();
    }
    }

    public void handleUsersTabSelectionChanged(ObservableValue observable, Object olsValue, Object newValue) {
        btnDeleteDocument.setVisible(true);
        btnModifyDocument.setVisible(true);

    }

    public void handleButtonAction(ActionEvent event) {
        if ((Button) event.getSource() == btnModifyDocument) {

            try {
                NewDocumentController controller = new NewDocumentController();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/GU_NewDocument.fxml"));
                Parent root = (Parent) loader.load();
                controller = ((NewDocumentController) loader.getController());
                doc = new Document();
                doc = (Document) tableDocuments.getSelectionModel().getSelectedItem();
                controller.initStage(root, doc, usuario);
            } catch (IOException ex) {
                Logger.getLogger(tabUsersController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if ((Button) event.getSource() == btnDeleteDocument) {
            DocumentClientService documentService = new DocumentClientService();
            Document deleteDoc = new Document();
            deleteDoc = (Document) tableDocuments.getSelectionModel().getSelectedItem();
            documentService.remove(deleteDoc.getId());
            insertData();
        }
    }

    public void chargeAllDocs() {

        List<Document> list = new ArrayList<Document>(documentList);
        ObservableList<Document> docList = FXCollections.observableArrayList(list);
        tableDocuments.setItems(docList);

    }

    private void chargeDisabledDocs() {
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

    private void chargeEnabledUsers() {
        if (usuario.getPrivilege() == UserPrivilege.SUPERADMIN || usuario.getPrivilege()==UserPrivilege.COMPANYADMIN) {
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
    }

