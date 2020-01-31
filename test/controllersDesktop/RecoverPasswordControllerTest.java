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
public class RecoverPasswordControllerTest extends ApplicationTest{
    
    @Override
    public void start(Stage stage) throws Exception {
        new clienteEscritorio.DesktopClient().start(stage);
    }

    @Before
    public void setUp() {
        clickOn("#hplRecoverPassword");
    }
    @Test
    public void test_a_recoverPassword(){
        clickOn("#TextFieldEmail").write("yerayram97@gmail.com");
        clickOn("#btnSend");
        clickOn("#btnEmailOk");
        
    }
}
