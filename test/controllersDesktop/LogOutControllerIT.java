/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import java.awt.event.MouseEvent;
import javafx.stage.Stage;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.matcher.control.LabeledMatchers;

/**
 *
 * @author Yeray
 */
public class LogOutControllerIT extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new clienteEscritorio.DesktopClient().start(stage);
    }

    @Before
    public void setUp() {
        doubleClickOn("#textFieldUsername").write("lleri");
        doubleClickOn("#textFieldPassword").write("serv1doR");
        clickOn("#btnLogIn");
    }

    
    @Test
    public void test_a_labelRellenados() {
        FxAssert.verifyThat("#txtNombreUsu", LabeledMatchers.hasText("Nombre completo: Yerray Ramos"));
        FxAssert.verifyThat("#txtLogin", LabeledMatchers.hasText("Usuario: lleri"));
        FxAssert.verifyThat("#txtEntity", LabeledMatchers.hasText("Entidad/Empresa: Mahou"));
        FxAssert.verifyThat("#txtPrivilege", LabeledMatchers.hasText("Tipo usuario: SUPERADMIN"));
        FxAssert.verifyThat("#txtEmail", LabeledMatchers.hasText("Email: yeray@gmail.com"));
    }

    @Test
    public void test_b_botones() {
        FxAssert.verifyThat("#btnUsers", isEnabled());
        FxAssert.verifyThat("#btnEntity", isEnabled());
        FxAssert.verifyThat("#btnDocuments", isEnabled());
        FxAssert.verifyThat("#btnDepartments", isEnabled());
        FxAssert.verifyThat("#btnAreas", isEnabled());
        FxAssert.verifyThat("#btnModify", isEnabled());
    }

    @Test
    public void test_c_btnWorking() {
        clickOn("#btnUsers");
        FxAssert.verifyThat("#tabUsers", isVisible());
        clickOn("#btnEntity");
        FxAssert.verifyThat("#tabEntity", isVisible());
        clickOn("#btnDocuments");
        FxAssert.verifyThat("#tabDocs", isVisible());
        clickOn("#btnDepartments");
        FxAssert.verifyThat("#tabDepart", isVisible());
        clickOn("#btnAreas");
        FxAssert.verifyThat("#tabAreas", isVisible());
        clickOn("#btnModify");
        FxAssert.verifyThat("#signUpPane", isVisible());
    }
     
    @Test
    public void test_d_menuItems() {
        rightClickOn("#paneUser");
        FxAssert.verifyThat("#cm", isVisible());
        clickOn("#dataItemUsers");
        FxAssert.verifyThat("#tabUsers", isVisible());
        rightClickOn("#paneUser");
        clickOn("#dataItemEntity");
        FxAssert.verifyThat("#tabEntity", isVisible());
        rightClickOn("#paneUser");
        clickOn("#dataItemDocs");
        FxAssert.verifyThat("#tabDocs", isVisible());
        rightClickOn("#paneUser");
        clickOn("#dataItemDepart");
        FxAssert.verifyThat("#tabDepart", isVisible());
        rightClickOn("#paneUser");
        clickOn("#dataItemArea");
        FxAssert.verifyThat("#tabAreas", isVisible());
    }

}
