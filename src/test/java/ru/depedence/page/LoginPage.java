package ru.depedence.page;

public class LoginPage extends BasePage {

    public LoginPage open() {
        open("/login");
        return this;
    }

    public LoginPage fillEmail(String email) {
        $(".email-input").setValue(email);
        return this;
    }

    public LoginPage fillPassword(String password) {
        $(".password-input").setValue(password);
        return this;
    }

    public LoginPage clickLoginBtn() {
        $("button[type='submit']").click();
        return this;
    }

}