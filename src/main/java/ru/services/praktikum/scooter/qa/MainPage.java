package ru.services.praktikum.scooter.qa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.Set;

public class MainPage {
    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public MainPage() {
    }

    // URL главной страницы сайта
    protected String mainPageUrl = "https://qa-scooter.praktikum-services.ru/";
    // Кнопка принятия кук
    private final By acceptCookieBtn = By.id("rcc-confirm-button");
    // Аккордеон
    private final By arrowPointerPrice = By.id("accordion__heading-0");
    private final By arrowPointerPriceText = By.xpath("//div[@aria-labelledby='accordion__heading-0']");
    // Кнопки аренды самоката
    private final By rentScooterHeaderBtn = By.xpath("//*[contains(@class,'Header_Header')]//button[contains(text(),'Заказать')]");
    private final By rentScooterFooterBtn = By.xpath("//*[contains(@class,'Home_FinishButton')]/*[contains(text(),'Заказать')]");
    // Лого самоката и яндекса
    private final By samokatLogo = By.xpath("//*[contains(@class,'Header_LogoScooter')]");
    private final By yandexLogo = By.xpath("//*[contains(@class,'Header_LogoYandex')]");
    // Элементы проверки статуса заказа
    private final By orderStatusText = By.xpath("//*[contains(@class,'Header_Link')][contains(text(),'Статус заказа')]");
    private final By orderStatusField = By.cssSelector("input[placeholder='Введите номер заказа']");
    private final By orderStatusGoBtn = By.xpath("//*[contains(@class,'Button_Button')][contains(text(),'Go!')]");


    void waitForPageLoaded() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("root")));
    }

    void arrowPointerPriceClick() {
        driver.findElement(arrowPointerPrice).click();
    }

    String arrowPointerPriceGetText() {
        return driver.findElement(arrowPointerPriceText).getText();
    }

    void clickAcceptCookieBtn() {
        driver.findElement(acceptCookieBtn).click();
    }

    void clickRentScooterHeaderBtn() {
        driver.findElement(rentScooterHeaderBtn).click();
    }

    void clickRentScooterFooterBtn() {
        driver.findElement(rentScooterFooterBtn).click();
    }

    void clickOnSamokatLogo() {
        driver.findElement(samokatLogo).click();
    }

    void clickOnYandexLogo() {
        driver.findElement(yandexLogo).click();
    }

    void waitForNewBrowserTabOpened() {
        Set<String> numberOfTabsOpened = Collections.singleton(driver.getWindowHandle());

        (new WebDriverWait(driver, Duration.ofSeconds(3))).
                until(ExpectedConditions.numberOfWindowsToBe(numberOfTabsOpened.size() + 1));
    }

    void switchToNewBrowserTab() {
        Object[] windowHandles = driver.getWindowHandles().toArray();
        driver.switchTo().window((String) windowHandles[1]);
    }

    void orderStatusEnterValue(String value) {
        driver.findElement(orderStatusText).click();

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(orderStatusField));

        driver.findElement(orderStatusField).sendKeys(value);
    }

    void clickOrderStatusGoBtn() {
        driver.findElement(orderStatusGoBtn).click();
    }

}
