package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement infoCard = $("[data-test-id=dashboard]");



    public DashboardPage() {
        infoCard.shouldBe(Condition.visible);

    }


}
