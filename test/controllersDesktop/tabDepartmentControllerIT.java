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
public class tabDepartmentControllerIT extends ApplicationTest {

    public void start(Stage stage) throws Exception {
        new clienteEscritorio.DesktopClient().start(stage);
    }

    @Before
    public void setUp() {
        doubleClickOn("#textFieldUsername").write("lleri");
        doubleClickOn("#textFieldPassword").write("serv1doR");
        clickOn("#btnLogIn");
        clickOn("#btnDepartments");
    }

    @Test
    public void test_a_searchDepart() {
        clickOn("#btnSearchDepartment");
        FxAssert.verifyThat("otros", isVisible());
    }
    @Test
    public void test_b_btnNew(){
        clickOn("#btnNewDepartment");
        FxAssert.verifyThat("#paneNewDepart", isVisible() );
    }

    @Test
    public void test_c_modifyDepart() {
        clickOn("#btnSearchDepartment");
        clickOn("otros");
        clickOn("#btnModifyDepartment");
        FxAssert.verifyThat("#paneNewDepart", isVisible());
    }
    @Test
    public void test_d_deleteDepart(){
        clickOn("#btnSearchDepartment");
        clickOn("informatica");
        clickOn("#btnDeleteDepartment");
    }
    

}
