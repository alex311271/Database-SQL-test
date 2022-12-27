package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.User;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    SelenideElement codInput = $("[data-test-id=code] input");
    SelenideElement verifyButton = $("[data-test-id=action-verify");

    public VerificationPage() {
        codInput.shouldBe(Condition.visible);
    }

    public DashboardPage codeInput(String cod) {
        codInput.setValue(cod);
        verifyButton.click();
        return new DashboardPage();
    }
}
