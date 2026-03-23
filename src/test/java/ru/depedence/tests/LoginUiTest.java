package ru.depedence.tests;

import org.junit.jupiter.api.Test;
import ru.depedence.core.BaseUiTest;
import ru.depedence.page.LoginPage;

public class LoginUiTest extends BaseUiTest {

    @Test
    void userCanLogin() {
        LoginPage page = new LoginPage()
                .open()
                .fillEmail("yandex@yandex.ru")
                .fillPassword("123zxcqwe")
                .clickLoginBtn();
    }

}