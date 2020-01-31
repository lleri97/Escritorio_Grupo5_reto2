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
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

/**
 *
 * @author stone
 */
public class NewDocumentControllerTest extends ApplicationTest {

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
        clickOn("#btnDocuments");
        clickOn("#btnNewDocument");
    }

    @Test
    public void test_a_newDocNull() {
        FxAssert.verifyThat("#paneNewDoc", isVisible());

        clickOn("#textFielTittle").write("prueba");
        clickOn("#textAreaDescription").write("descripcion de prueba");
        clickOn("#hyperlinkDocument");
        closeCurrentWindow();
        FxAssert.verifyThat("#paneNewDoc", isVisible());
        clickOn("#btnAddDocument");
        FxAssert.verifyThat("#okButtonDocNull", isVisible());
        clickOn("#okButtonDocNull");
    }

    @Test
    public void test_b_newDocNullText() {
        clickOn("#textFielTittle").write("");
        clickOn("#textAreaDescription").write("");
        clickOn("#btnAddDocument");
        clickOn("#okButtonDocNull");
    }
}
