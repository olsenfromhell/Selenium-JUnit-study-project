package ru.services.praktikum.scooter.qa;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPageTests extends MainPage {
    WebDriver driver = new ChromeDriver();
    MainPage objMainPage = new MainPage(driver);
    TrackingPage objTrackingPage = new TrackingPage(driver);


    public MainPageTests() {
        super();
    }

    @Before
    public void setup() {
        driver.get(mainPageUrl);
        objMainPage.waitForPageLoaded();
        objMainPage.clickAcceptCookieBtn();
    }

    // Тест проверяет текст в первой выпадашке аккордеона
    @Test
    public void checkArrowPointerText() {
        String arrowPointerText;

        objMainPage.arrowPointerPriceClick();
        arrowPointerText = objMainPage.arrowPointerPriceGetText();

        Assert.assertEquals("Сутки — 400 рублей. Оплата курьеру — наличными или картой.", arrowPointerText);
    }

    // Тест проверяет что при нажатии на логотип «Самоката», попадаешь на главную страницу «Самоката»
    @Test
    public void navigateToHomePageOnLogoClick() {
        driver.get("https://qa-scooter.praktikum-services.ru/order");

        objMainPage.clickOnSamokatLogo();

        String currentUrl = driver.getCurrentUrl();

        Assert.assertEquals("После клика на логотип Самоката текущий URL не совпадает с заданным", mainPageUrl, currentUrl);
    }

    // Тест проверяет что при нажатии на логотип Яндекса, в новом окне откроется главная страница Яндекса.
    @Test
    public void navigateToYandexPageOnLogoClick() {
        objMainPage.clickOnYandexLogo();
        objMainPage.waitForNewBrowserTabOpened();

        objMainPage.switchToNewBrowserTab();
        String currentUrl = driver.getCurrentUrl();

        Assert.assertEquals("После клика на логотип Яндекса текущий URL не совпадает с заданным", "https://yandex.ru/", currentUrl);
    }

    // Тест проверяет что при неправильно введенном номере заказа пользователь попадает на страницу статуса заказа
    // и видит картинку "Такого заказа нет"
    @Test
    public void checkOrderStatusWithInvalidData() {
        String trackOrderPageUrl = "https://qa-scooter.praktikum-services.ru/track?t=";
        String orderStatusNumber = "0000";

        objMainPage.orderStatusEnterValue(orderStatusNumber);
        objMainPage.clickOrderStatusGoBtn();

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(objTrackingPage.orderNotFoundImage));

        Assert.assertEquals(
                "Трекинг-ссылка отличается от ожидаемой", trackOrderPageUrl + orderStatusNumber, driver.getCurrentUrl()
        );
    }

    @After
    public void quit() {
        driver.quit();
    }
}
