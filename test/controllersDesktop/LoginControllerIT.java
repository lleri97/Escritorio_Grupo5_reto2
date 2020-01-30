/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllersDesktop;

import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.ALT;
import static javafx.scene.input.KeyCode.C;
import static javafx.scene.input.KeyCode.H;
import static javafx.scene.input.KeyCode.L;
import static javafx.scene.input.KeyCode.W;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;

/**
 *
 * @author Yeray
 */
public class LoginControllerIT extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new clienteEscritorio.DesktopClient().start(stage);
    }

    @Test
    public void test_a_PasswordNull() {
        doubleClickOn("#textFieldUsername").write("username");
        clickOn("#btnLogIn");
        FxAssert.verifyThat("#okButtonCamposVacios", isEnabled());
        clickOn("#okButtonCamposVacios");
    }

    @Test
    public void test_b_UserNull() {
        doubleClickOn("#textFieldPassword").write("username");
        clickOn("#btnLogIn");
        FxAssert.verifyThat("#okButtonCamposVacios", isEnabled());
        clickOn("#okButtonCamposVacios");
    }

    @Test
    public void test_c_LoginError() {
        doubleClickOn("#textFieldUsername").write("FalseLogin");
        doubleClickOn("#textFieldPassword").write("serv1doR");
        clickOn("#btnLogIn");
        FxAssert.verifyThat("#okButtonLoginIncorrecto", isEnabled());
        clickOn("#okButtonLoginIncorrecto");
    }

    @Test
    public void test_d_PasswordError() {
        doubleClickOn("#textFieldUsername").write("lleri");
        doubleClickOn("#textFieldPassword").write("contraseña");
        clickOn("#btnLogIn");
        FxAssert.verifyThat("#okButtonPasswordIncorrecto", isEnabled());
        clickOn("#okButtonPasswordIncorrecto");
    }

        @Test
    public void test_e_MnemonicsLogin(){
        doubleClickOn("#textFieldUsername").write("lleri");
        doubleClickOn("#textFieldPassword").write("serv1doR");
        press(ALT).press(L);
    }
  
    @Test
    public void test_f_MnemonicsHelp(){
        press(ALT).press(H);
        press(KeyCode.CONTROL).press(W);
    }
}
