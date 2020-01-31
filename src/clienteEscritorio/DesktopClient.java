package clienteEscritorio;

import controllersDesktop.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * Class that loads the application and the first window.
 *
 * @author Fran
 */
public class DesktopClient extends Application {

    /**
     * Method to create the window and start the JavaFX applicaction
     *
     * @param stage escena necesaria para cargar la ventana
     * @throws Exception excepcion generica
     */
    public void start(Stage stage) throws Exception {
        //Load node graph from fxml file
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxmlWindows/GU01_Login.fxml"));
        Parent root = (Parent) loader.load();
        //Get controller for graph 
        LoginController stageController = ((LoginController) loader.getController());
        //Set a reference for Stage
        stageController.setStage(stage);
        //Initializes primary stage
        stageController.initStage(root);
    }

    /**
     * Main method
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
