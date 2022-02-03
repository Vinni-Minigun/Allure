package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Feature;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

@Feature("Тестируем функциональность доставки карты")
public class DeliveryOrderDateTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.DELETE);
    }

    //   @AfterAll
    // static void tearDownAll() {
//        SelenideLogger.removeListener("allure");
//  }

    @DisplayName("Проверка перепланирования встречи с курьером на другую дату")
    @Test
    public void shouldCardDeliveryOrder() {
        val user = DataGenerator.Registration.generateInfo("ru");
        $("[data-test-id=city] input").val(user.getCity());
        $("[data-test-id=date] input").val(DataGenerator.generateDate(3));
        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").val(user.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//*[text()='Запланировать']").click();
        $("[data-test-id=success-notification]")
                .shouldHave(text("Успешно! Встреча успешно запланирована на " + DataGenerator.generateDate(3)), Duration.ofSeconds(15));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.DELETE);
        $("[data-test-id=date] input").val(DataGenerator.generateDate(5));
        $x("//*[text()='Запланировать']").doubleClick();
        $(withText("У вас уже запланирована встреча на другую дату")).shouldHave(text("Перепланировать?"), Duration.ofSeconds(1500));
        $(byText("Перепланировать")).click();
        $("[data-test-id=success-notification]")
                .shouldHave(text("Успешно! Встреча успешно запланирована на " + DataGenerator.generateDate(5)), Duration.ofSeconds(1500));

    }
}