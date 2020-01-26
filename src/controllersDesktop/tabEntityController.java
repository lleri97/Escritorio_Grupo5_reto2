/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.Company;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javax.ws.rs.core.GenericType;
import servicesRestfull.CompanyClientService;
import servicesRestfull.DepartmentClientService;

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
    @FXML
    

    
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
    }
/*
        btnNewCompany.setOnAction((event) -> {
            lanzarNewEntityWindow();
        });
        btnSearchEntity.setOnAction((event) -> {
           insertData();
        });

    }
    
     public void handleDepartmentTabSelectionChanged(ObservableValue observable, Object olsValue, Object newValue) {
        btnDeleteDepartment.setVisible(true);
        btnModifyDepartment.setVisible(true);

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
    
    public void insertData() {
        columnID.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        departmentService = new DepartmentClientService();
        departmentList = new HashSet<Department>();
        departmentList = departmentService.FindAllDepartment(new GenericType<Set<Department>>() {
        });

        List<Department> list = new ArrayList<Department>(departmentList);
        ObservableList<Department> departList = FXCollections.observableArrayList(list);
        tableDepartments.setItems(departList);
    }
*/
}
