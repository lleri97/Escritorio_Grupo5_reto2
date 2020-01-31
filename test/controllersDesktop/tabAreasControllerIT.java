/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.util.NodeQueryUtils.isVisible;

/**
 *
 * @author Yeray
 */
public class tabAreasControllerIT extends ApplicationTest {
    
    public void start(Stage stage) throws Exception {
        new clienteEscritorio.DesktopClient().start(stage);
    }

    @Before
    public void setUp() {
        doubleClickOn("#textFieldUsername").write("lleri");
        doubleClickOn("#textFieldPassword").write("serv1doR");
        clickOn("#btnLogIn");
        clickOn("#btnAreas");
    }
    
    @Test
    public void test_a_readAreas(){
        clickOn("#btnSearchAreas");
        clickOn("general");
        clickOn("#btnModifyArea");
        FxAssert.verifyThat("#paneNewArea", isVisible());
    }
    @Test
    public void test_b_DeleteArea(){
         clickOn("#btnSearchAreas");
        clickOn("general");
        clickOn("#btnDeleteArea");
    }
    
}
