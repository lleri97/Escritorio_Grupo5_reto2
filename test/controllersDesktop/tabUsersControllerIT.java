/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Boolean;
import entitiesModels.User;
import java.io.IOException;
import java.util.function.Predicate;
import javafx.stage.Stage;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import org.eclipse.persistence.jpa.jpql.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author Yeray
 */
public class tabUsersControllerIT extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new clienteEscritorio.DesktopClient().start(stage);
    }

    @Before
    public void setUp() {
        doubleClickOn("#textFieldUsername").write("lleri");
        doubleClickOn("#textFieldPassword").write("serv1doR");
        clickOn("#btnLogIn");
        clickOn("#btnUsers");
    }

    @Test
    public void test_a_searchUser_Enabled() {
        clickOn("#btnSearch");
        clickOn("#tableUsers");
        FxAssert.verifyThat("Yerray Ramos", isVisible());
        clickOn("#btnModifyUser");
        FxAssert.verifyThat("#signUpPane", isVisible());
    }

    @Test
    public void test_b_searchUser_Disabled() {
        clickOn("#chkBoxHabilitado");
        clickOn("#chkBoxDeshabilitado");
        clickOn("#btnSearch");
        FxAssert.verifyThat("Camarón de la Isla", isVisible());
    }

    @Test
    public void test_c_deleteUser() {
        clickOn("#btnSearch");
        clickOn("#tableUsers");
        clickOn("#btnDeleteUser");
    }

    @Test
    public void test_d_searchUsers_empty() {
        clickOn("#chkBoxHabilitado");
        clickOn("#btnSearch");
        FxAssert.verifyThat("#okButtonChkNoSelected", isVisible());
        clickOn("#okButtonChkNoSelected");
    }

}
