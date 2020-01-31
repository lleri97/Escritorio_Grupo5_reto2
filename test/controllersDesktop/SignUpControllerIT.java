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
public class SignUpControllerIT extends ApplicationTest {

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
        FxAssert.verifyThat("Yeray Ramos", isVisible());
        closeCurrentWindow();

    }

    @Test
    public void test_b_newUser() {
        clickOn("#btnSearch");
        clickOn("prueba");
        clickOn("#btnModifyUser");
        FxAssert.verifyThat("#signUpPane", isVisible());
        doubleClickOn("#textFullName").eraseText(20);
        doubleClickOn("#textFullName").write("prueba");
        doubleClickOn("#dPickerNacimiento").eraseText(20).write("6/01/2020");
        doubleClickOn("#textPassword").write("Abcd*1234");
        doubleClickOn("#textConfirmPassword").write("Abcd*1234");
        clickOn("#btnCreateUser");
        clickOn("#okButtonModify");
        clickOn("#btnSearch");
        FxAssert.verifyThat("prueba", isVisible());
        closeCurrentWindow();

    }

    @Test
    public void test_c_passwordNoMatch() {
        clickOn("#btnSearch");
        clickOn("prueba");
        clickOn("#btnModifyUser");
        FxAssert.verifyThat("#signUpPane", isVisible());
        doubleClickOn("#textFullName").eraseText(20);
        doubleClickOn("#textFullName").write("prueba");
        doubleClickOn("#dPickerNacimiento").write("6/01/2020");
        doubleClickOn("#textPassword").write("Abcd*1234");
        doubleClickOn("#textConfirmPassword").write("Abcd*12345");
        clickOn("#btnCreateUser");
        clickOn("#okButtonNoMatch");
        closeCurrentWindow();

    }

    @Test
    public void test_d_noData() {
        clickOn("#btnSearch");
        clickOn("prueba");
        clickOn("#btnModifyUser");
        FxAssert.verifyThat("#signUpPane", isVisible());
        doubleClickOn("#textFullName").eraseText(20);
        doubleClickOn("#textFullName").write("prueba");
        doubleClickOn("#dPickerNacimiento").eraseText(20);
        doubleClickOn("#textPassword").write("");
        doubleClickOn("#textConfirmPassword").write("");
        clickOn("#btnCreateUser");
        clickOn("#okbuttonError");
        closeCurrentWindow();

    }

    @Test
    public void test_e_passwordFormat() {
        clickOn("#btnSearch");
        clickOn("prueba");
        clickOn("#btnModifyUser");
        FxAssert.verifyThat("#signUpPane", isVisible());
        doubleClickOn("#textFullName").eraseText(20);
        doubleClickOn("#textFullName").write("prueba");
        doubleClickOn("#dPickerNacimiento").write("6/01/2020");
        doubleClickOn("#textPassword").write("miau");
        doubleClickOn("#textConfirmPassword").write("miau");
        clickOn("#btnCreateUser");
        clickOn("#okbuttonError");
        closeCurrentWindow();
    }

    @Test
    public void test_f_tooManyCharacters() {
        clickOn("#btnSearch");
        clickOn("prueba");
        clickOn("#btnModifyUser");
        FxAssert.verifyThat("#signUpPane", isVisible());
        doubleClickOn("#textFullName").eraseText(20);
        doubleClickOn("#textFullName").write("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        doubleClickOn("#dPickerNacimiento").write("6/01/2020");
        doubleClickOn("#textPassword").write("");
        doubleClickOn("#textConfirmPassword").write("");
        clickOn("#btnCreateUser");
        clickOn("#okbuttonError");
        closeCurrentWindow();

    }
    

    @Test
    public void test_g_mailAlreadyInUse() {
        clickOn("#btnSearch");
        FxAssert.verifyThat("yerayram97@gmail.com", isVisible());
        clickOn("prueba");
        clickOn("#btnCreateUser");
        FxAssert.verifyThat("#signUpPane", isVisible());
        doubleClickOn("#textFullName").eraseText(20);
        doubleClickOn("#textFullName").write("prueba");
        doubleClickOn("#dPickerNacimiento").eraseText(20).write("6/01/2020");
        doubleClickOn("#textPassword").write("Abcd*1234");
        doubleClickOn("#textConfirmPassword").write("Abcd*1234");
        doubleClickOn("#textEmail").eraseText(20).write("yerayram97@gmail.com");
        clickOn("#btnCreateUser");
        clickOn("#okbuttonError");
    }

}
