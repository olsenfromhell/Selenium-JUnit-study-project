package ru.services.praktikum.scooter.qa;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage extends MainPage {
    private WebDriver driver;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public OrderPage() {
    }

    // Поля для заполнения формы "Для кого самокат"
    protected String renterName;
    protected String renterLastName;
    protected String renterScooterDeliveryAddress;
    protected String renterPhone;
    protected String renterMetroStation;
    // Кнопка 'Заказать' в форме "Хотите оформить заказ?"
    private final By rentScooterOrderFormBtn = By.xpath("//*[contains(@class,'Order_Buttons')]/*[contains(text(),'Заказать')]");
    // кнопки модального окна "Хотите оформить заказ?"
    private final By rentScooterConfirmBtn = By.xpath("//*[contains(@class,'Button_Button')][contains(text(), 'Да')]");
    // Элементы формы "Для кого самокат"
    private final By renterNameField = By.cssSelector("input[placeholder='* Имя']");
    private final By renterLastNameField = By.cssSelector("input[placeholder='* Фамилия']");
    private final By renterScooterDeliveryAddressField = By.cssSelector("input[placeholder='* Адрес: куда привезти заказ']");
    private final By renterMetroStationField = By.cssSelector("input[placeholder='* Станция метро']");
    private final By renterPhoneField = By.cssSelector("input[placeholder='* Телефон: на него позвонит курьер']");
    private final By rentFormContinueBtn = By.xpath("//*[contains(text(), 'Далее')]");
    private final By renterMetroStationSelector = By.xpath("//*[@class='select-search__select']");
    // Элементы формы "Про аренду"
    private final By rentScooterDeliveryDateField = By.cssSelector("input[placeholder='* Когда привезти самокат']");
    private final By rentScooterDeliveryDateDatePicker = By.xpath("//*[contains(text(), '15')]");
    private final By rentScooterRentalPeriodField = By.cssSelector("div[class='Dropdown-placeholder']");
    private final By rentScooterRentalPeriodSelector = By.cssSelector("div[class='Dropdown-option']");
    // Элементы модалки успешного оформления заказа самоката
    private final By rentScooterSuccessModal = By.xpath("//*[contains(text(), 'Заказ оформлен')]");
    // Тексты ошибок при заполнении формы заказа самоката невалидными данными
    protected final By rentScooterNameFieldErrorMessage = By.xpath("//*[contains(@class,'Input_ErrorMessage')][contains(text(),'Введите корректное имя')]");
    protected final By rentScooterLastNameFieldErrorMessage = By.xpath("//*[contains(@class,'Input_ErrorMessage')][contains(text(),'Введите корректную фамилию')]");
    protected final By rentScooterDeliveryAddressFieldErrorMessage = By.xpath("//*[contains(@class,'Input_ErrorMessage')][contains(text(),'Введите корректный адрес')]");
    protected final By rentScooterPhoneFieldErrorMessage = By.xpath("//*[contains(@class,'Input_ErrorMessage')][contains(text(),'Введите корректный номер')]");


    // Заполнение формы "Для кого самокат"
    void fillRenterInfoForm(
            String renterName, String renterLastName, String renterScooterDeliveryAddress,
            String renterMetroStation, String renterPhone
    ) {
        driver.findElement(renterNameField).sendKeys(renterName);
        driver.findElement(renterLastNameField).sendKeys(renterLastName);
        driver.findElement(renterScooterDeliveryAddressField).sendKeys(renterScooterDeliveryAddress);
        driver.findElement(renterMetroStationField).sendKeys(renterMetroStation);
        driver.findElement(renterMetroStationSelector).click();
        driver.findElement(renterPhoneField).sendKeys(renterPhone);

    }

    // Перегружаем метод заполнения формы "Для кого самокат" (станция метро не заполняется)
    void fillRenterInfoForm(
            String renterName, String renterLastName, String renterScooterDeliveryAddress, String renterPhone
    ) {
        driver.findElement(renterNameField).sendKeys(renterName);
        driver.findElement(renterLastNameField).sendKeys(renterLastName);
        driver.findElement(renterScooterDeliveryAddressField).sendKeys(renterScooterDeliveryAddress);
        driver.findElement(renterPhoneField).sendKeys(renterPhone);
        // убрать фокус с поля для теста checkScooterOrderFormErrors() в OrderPageTests.java
        driver.findElement(renterPhoneField).sendKeys(Keys.TAB);
    }

    void clickRentFormContinueBtn() {
        driver.findElement(rentFormContinueBtn).click();
    }

    // Заполнение формы "Про аренду"
    void fillAboutRentForm() {
        driver.findElement(rentScooterDeliveryDateField).click();
        driver.findElement(rentScooterDeliveryDateDatePicker).click();
        driver.findElement(rentScooterRentalPeriodField).click();
        driver.findElement(rentScooterRentalPeriodSelector).click();
    }

    void clickRentScooterOrderFormBtn() {
        driver.findElement(rentScooterOrderFormBtn).click();
    }

    void clickRentScooterConfirmBtn() {
        driver.findElement(rentScooterConfirmBtn).click();
    }

    void waitForScooterOrderSuccessModal() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(rentScooterSuccessModal));
    }
}
