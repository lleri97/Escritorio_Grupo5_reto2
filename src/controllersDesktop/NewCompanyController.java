/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import entitiesModels.Company;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import servicesRestfull.CompanyClientService;
import utils.UtilsWindows;
import utils.Validator;

/**
 * FXML Controller class
 *
 * @author Fran
 */
public class NewCompanyController {

    @FXML
    private TextField textFieldNameCompany;
    @FXML
    private Label lblName;
    @FXML
    private Label lblError;
    @FXML
    private TextField textFieldCif;
    @FXML
    private Label lblCif;
    @FXML
    private Button btnAddCompany;
    @FXML
    private Button btnCancel;

    private static final Logger LOGGER = Logger.getLogger(LogOutController.class.getPackage() + "." + LogOutController.class.getName());

    private CompanyClientService companyService = new CompanyClientService();

    Stage stage;

    /**
     * Inicializacion de la ventana de nueva compañia
     *
     * @param root elemento raiz
     * @param company objeto del tipo Company
     */
    public void initStage(Parent root, Company company, String mod) {

        Scene sceneNewEntity = new Scene(root);
        stage = new Stage();
        stage.setScene(sceneNewEntity);
        stage.setResizable(false);

        stage.initModality(Modality.APPLICATION_MODAL);
        lblError.setVisible(false);
        UtilsWindows alert = new UtilsWindows();
        if (mod == "modify") {
            LOGGER.info("Cargando ventana de modificar compañia");
            textFieldNameCompany.setText(company.getName());
            textFieldCif.setText(company.getCif());
            stage.setTitle("Editar compañía");
            btnAddCompany.setText("Confirmar");

            btnAddCompany.setOnAction((event) -> {
                if (Validator.cifChecker(textFieldCif.getText())) {
                    LOGGER.info("Preparando para editar compañia en la base de datos");
                    Company comp = new Company();
                    comp.setId(company.getId());
                    comp.setCif(textFieldCif.getText());
                    comp.setName(textFieldNameCompany.getText());
                    companyService.edit(comp);
                    LOGGER.info("Compañia editada con exito");
                }else{
                    LOGGER.info("El CIF no tiene el formato correcto.");
                    alert.alertWarning("Aviso", "El CIF tiene que tener un formato correcto.", "");
                }

            });
        } else {
            LOGGER.info("Cargando ventana de nueva compañia");
            stage.setTitle("Nueva compañía");
            
            btnAddCompany.setOnAction((event) -> {
                LOGGER.info("Preparando para añadir compañia a la base de datos");
                addCompany();
                LOGGER.info("Compañia añadida con exito a la base de datos");
            });
        }

        btnCancel.setOnAction((event) -> {
            stage.close();
        });
        LOGGER.info("Ventana inicializada con exito");
        stage.show();

    }

    /**
     * Metodo para añadir compañia al servidor
     */
    public void addCompany() {
        Company comp = new Company();
        companyService = new CompanyClientService();

        comp.setCif(textFieldCif.getText());
        comp.setName(textFieldNameCompany.getText());
        companyService.create(comp);

    }
}
