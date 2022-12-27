package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginInput = $("[data-test-id=login] input");
    private SelenideElement passwordInput = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login");
    private SelenideElement errorMessege = $("[data-test-id=error-notification");

    public VerificationPage logInn(String login, String password){
        loginInput.setValue(login);
        passwordInput.setValue(password);
        loginButton.click();
        return new VerificationPage();
    }
    private void cleanForm() {
        loginInput.doubleClick();
        loginInput.sendKeys(Keys.DELETE);
        passwordInput.doubleClick();
        passwordInput.sendKeys(Keys.DELETE);
    }

    public LoginPage incorrectPassword(String login, String password) {
        cleanForm();
        loginInput.setValue(login);
        passwordInput.setValue(password);
        loginButton.click();
        errorMessege.shouldBe(Condition.visible);
        return new LoginPage();
    }

    public LoginPage tripleIncorrecItnput(String login, String password) {
        for (int run = 0; run < 3; run++) {
            incorrectPassword(login, password);
        }
        loginButton.shouldBe(Condition.disabled);
        return new LoginPage();
    }
}
