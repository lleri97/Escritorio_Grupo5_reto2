/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import javafx.stage.Stage;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

/**
 *
 * @author Yeray
 */
public class tabDocumentsControllerIT extends ApplicationTest {

    public void start(Stage stage) throws Exception {
        new clienteEscritorio.DesktopClient().start(stage);
    }

    @Before
    public void setUp() {
        doubleClickOn("#textFieldUsername").write("lleri");
        doubleClickOn("#textFieldPassword").write("serv1doR");
        clickOn("#btnLogIn");
        clickOn("#btnDocuments");
    }

    @Test
    public void test_a_SearchDocsEnabled() {
        clickOn("#btnSearch");
        Assert.assertTrue("#tableDocuments".isEmpty());
    }

    @Test
    public void test_b_SearchDocsDisabled() {
        clickOn("#chkBoxEnabled");
        clickOn("#chkBoxDisabled");
        clickOn("#btnSearch");
        FxAssert.verifyThat("python", isVisible());
    }

    @Test
    public void test_c_modifyDoc() {
        clickOn("#chkBoxEnabled");
        clickOn("#chkBoxDisabled");
        clickOn("#btnSearch");
        clickOn("python");
        clickOn("#btnModifyDocument");
        FxAssert.verifyThat("#paneNewDoc", isVisible());
    }

    @Test
    public void test_d_deleteDoc() {
        clickOn("#chkBoxEnabled");
        clickOn("#chkBoxDisabled");
        clickOn("#btnSearch");
        clickOn("baseDatos");
        clickOn("#btnDeleteDocument");
    }

    @Test
    public void test_e_DownloadDoc() {
        clickOn("#chkBoxEnabled");
        clickOn("#chkBoxDisabled");
        clickOn("#btnSearch");
        clickOn("python");
        clickOn("#btnDownloadDocument");
        closeCurrentWindow();
        FxAssert.verifyThat("#AnchorPaneDoc", isVisible());

    }

}
