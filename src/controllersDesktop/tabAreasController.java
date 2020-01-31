/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.Area;
import entitiesModels.Department;
import entitiesModels.User;
import entitiesModels.UserPrivilege;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javax.ws.rs.core.GenericType;
import servicesRestfull.AreaClientService;
import servicesRestfull.DepartmentClientService;

/**
 * FXML Controller class
 *
 * @author Yeray
 */
public class tabAreasController  {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane tabAreas;
    @FXML
    private Button btnNewArea;
    @FXML
    private Button btnSearchAreas;
    @FXML
    private Button btnDeleteArea;
    @FXML
    private Button btnModifyArea;
    @FXML
    private TableView tableAreas;
    @FXML
    private TableColumn columnName;
    
    private AreaClientService areaService;
    
    private User usuario;
    private Set <Area> areaList;
    Class responseType;
    
    
    public void initStage(User usuario){
        this.usuario=usuario;
        btnDeleteArea.setVisible(false);
        btnModifyArea.setVisible(false);
        if(usuario.getPrivilege()==UserPrivilege.USER){
            btnNewArea.setDisable(true);
            btnNewArea.setVisible(false);
            
        }
        
        btnNewArea.setOnAction((event) -> {
            Area area = new Area();
            String mod="";
            lanzarNewAreaWindow(area,mod);
        });
        btnSearchAreas.setOnAction((event)->{
            insertData();
        });
        btnDeleteArea.setOnAction((event)->{
            Area areaDelete = new Area();
            areaDelete= (Area) tableAreas.getSelectionModel().getSelectedItem();
            areaService.remove(areaDelete.getId());
            insertData();
        });
        btnModifyArea.setOnAction((event)->{
            Area area =new Area();
            area=(Area) tableAreas.getSelectionModel().getSelectedItem();
            String mod= "modify";
            lanzarNewAreaWindow(area,mod);
        });
                tableAreas.getSelectionModel().selectedItemProperty().addListener(this::handleAreasTabSelectionChanged);

    }
       public void lanzarNewAreaWindow(Area area, String mod) {
        
            try {
                NewAreaController controller = new NewAreaController();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlWindows/GU_NewArea.fxml"));
                Parent root = (Parent) loader.load();
                controller = ((NewAreaController) loader.getController());
                controller.initStage(root, usuario,area,mod);
            } catch (IOException ex) {
            }
        }

    public void insertData() {

        columnName.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        areaService = new AreaClientService();
        areaList = new HashSet<Area>();
        areaList = areaService.FindAllArea(new GenericType<Set<Area>>() {
        });
        List<Area> list = new ArrayList<Area>(areaList);
        ObservableList<Area> areaList = FXCollections.observableArrayList(list);
        tableAreas.setItems(areaList);
    }
     public void handleAreasTabSelectionChanged(ObservableValue observable, Object olsValue, Object newValue) {
        btnDeleteArea.setVisible(true);
        btnModifyArea.setVisible(true);

    }
    
}
