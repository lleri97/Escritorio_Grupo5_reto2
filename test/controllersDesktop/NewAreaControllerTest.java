/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author stone
 */
public class NewAreaControllerTest extends ApplicationTest{
    
    @Override
    public void start(Stage stage) throws Exception {
        new clienteEscritorio.DesktopClient().start(stage);
    }

    @Before
    public void setUp() {
        doubleClickOn("#textFieldUsername").write("lleri");
        doubleClickOn("#textFieldPassword").write("serv1doR");
        clickOn("#btnLogIn");
        FxAssert.verifyThat("#paneUser", isVisible());
        clickOn("#btnAreas");
        clickOn("#btnNewArea");
    }
    @Test
    public void test_a_newArea(){
        clickOn("#textFieldNameArea").write("pruebas");
        clickOn("#btnAddArea");
        clickOn("#btnCancel");
        clickOn("#btnSearchAreas");
        FxAssert.verifyThat("pruebas", isVisible());
    }
    
}
