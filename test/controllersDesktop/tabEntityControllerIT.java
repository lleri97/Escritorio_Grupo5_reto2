/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import javafx.stage.Stage;
import org.junit.Assert;
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
public class tabEntityControllerIT extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new clienteEscritorio.DesktopClient().start(stage);
    }

    @Before
    public void setUp() {
        doubleClickOn("#textFieldUsername").write("lleri");
        doubleClickOn("#textFieldPassword").write("serv1doR");
        clickOn("#btnLogIn");
        clickOn("#btnEntity");
    }

    @Test
    public void test_a_searchEntity() {
        clickOn("#btnSearchEntity");
        FxAssert.verifyThat("Mahou", isVisible());
        clickOn("Mahou");
        FxAssert.verifyThat("#btnModifyEntity", isVisible());
        FxAssert.verifyThat("#btnDeleteEntity", isVisible());
    }

    @Test
    public void test_b_newCompany() {
        clickOn("#btnNewCompany");
        FxAssert.verifyThat("#paneNewCompany", isVisible());
        clickOn("#btnCancel");
    }

    @Test
    public void test_c_Modify() {
        clickOn("#btnSearchEntity");
        clickOn("Mahou");
        clickOn("#btnModifyEntity");
        FxAssert.verifyThat("#paneNewCompany", isVisible());

    }

    @Test
    public void test_d_Delete() {
        clickOn("#btnSearchEntity");
        clickOn("Tartanga LH");
        clickOn("#btnDeleteEntity");
        assertTrue("Tartanga LH".isEmpty());
    }

}
