package controllersDesktop;

import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * HelpLogin Window Controller
 *
 * @author Fran
 */
public class LoginHelpController {

    @FXML
    private WebView wViewLogin;
    private static final Logger LOGGER = Logger.getLogger(LoginHelpController.class.getPackage() + "." + LoginHelpController.class.getName());
    

    /**
     * Metodo de inicializacion de ventana
     * 
     * @param root Elemento raiz de la ventana
     */
    public void initStage(Parent root) {
        LOGGER.info("Cargando la ventana de ayuda");
        Scene scene = new Scene(root);
        
        //The window starts
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Login mnemonics help");
        stage.setResizable(false);
        stage.setOnShowing(this::handleWindowShowing);
        stage.show();
        LOGGER.info("Ventana cargada correctamente");
    }
    /**
     * Metodo que se accion al mostrar la ventana
     * @param event 
     */
    private void handleWindowShowing(WindowEvent event) {
        WebEngine webEngine = wViewLogin.getEngine();
        //Load help page.
        webEngine.load(getClass()
                .getResource("/help_files/HelpLogin.html").toExternalForm());
    }

}