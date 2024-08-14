package ru.services.praktikum.scooter.qa;

import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.services.praktikum.DriverRule;
import ru.services.praktikum.EnvConfig;

import java.time.Duration;

public class MainPageTest extends MainPage {

    @Rule
    public DriverRule factory = new DriverRule();

    public MainPageTest() {
        super();
    }

    // Тест проверяет текст в первой выпадашке аккордеона
    @Test
    public void checkArrowPointerText() {
        WebDriver driver = factory.getDriver();
        MainPage objMainPage = new MainPage(driver);

        String arrowPointerText;

        objMainPage.openMainPage();
        objMainPage.clickAcceptCookieBtn();
        objMainPage.arrowPointerPriceClick();
        arrowPointerText = objMainPage.arrowPointerPriceGetText();

        Assert.assertEquals("Сутки — 400 рублей. Оплата курьеру — наличными или картой.", arrowPointerText);
    }

    // Тест проверяет что при нажатии на логотип «Самоката», попадаешь на главную страницу «Самоката»
    @Test
    public void navigateToHomePageOnLogoClick() {
        WebDriver driver = factory.getDriver();

        MainPage objMainPage = new MainPage(driver);
        OrderPage objOrderPage = new OrderPage(driver);

        objOrderPage.openOrderPage();
        objMainPage.clickOnSamokatLogo();

        String currentUrl = driver.getCurrentUrl();

        Assert.assertEquals("После клика на логотип Самоката текущий URL не совпадает с заданным", EnvConfig.BASE_URL, currentUrl);
    }

    // Тест проверяет что при нажатии на логотип Яндекса, в новом окне откроется главная страница Яндекса.
    @Test
    public void navigateToYandexPageOnLogoClick() {
        WebDriver driver = factory.getDriver();

        MainPage objMainPage = new MainPage(driver);

        objMainPage.openMainPage();
        objMainPage.clickAcceptCookieBtn();
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
        WebDriver driver = factory.getDriver();

        MainPage objMainPage = new MainPage(driver);
        TrackingPage objTrackingPage = new TrackingPage(driver);

        String trackOrderPageUrl = "https://qa-scooter.praktikum-services.ru/track?t=";
        String orderStatusNumber = "0000";

        objMainPage.openMainPage();
        objMainPage.clickAcceptCookieBtn();
        objMainPage.orderStatusEnterValue(orderStatusNumber);
        objMainPage.clickOrderStatusGoBtn();

        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(objTrackingPage.orderNotFoundImage));

        Assert.assertEquals(
                "Трекинг-ссылка отличается от ожидаемой", trackOrderPageUrl + orderStatusNumber, driver.getCurrentUrl()
        );
    }
}
