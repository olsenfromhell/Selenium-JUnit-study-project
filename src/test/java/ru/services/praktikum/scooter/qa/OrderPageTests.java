package ru.services.praktikum.scooter.qa;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@RunWith(Parameterized.class)
public class OrderPageTests extends OrderPage {
    private WebDriver driver;
    private final MainPage objMainPage;
    private final OrderPage objOrderPage;


    public OrderPageTests(
            String renterName, String renterLastName, String renterScooterDeliveryAddress,
            String renterMetroStation, String renterPhone, String browser
    ) {
        super.renterName = renterName;
        super.renterLastName = renterLastName;
        super.renterScooterDeliveryAddress = renterScooterDeliveryAddress;
        super.renterMetroStation = renterMetroStation;
        super.renterPhone = renterPhone;

        if (browser.equals("chrome")) {
            this.driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            this.driver = new FirefoxDriver();
        }

        objMainPage = new MainPage(driver);
        objOrderPage = new OrderPage(driver);
    }

    @Before
    public void setup() {
        driver.get(mainPageUrl);
        objMainPage.waitForPageLoaded();
        objMainPage.clickAcceptCookieBtn();
    }

    @Parameterized.Parameters
    public static Object[][] getRentalFormData() {
        return new Object[][]{
                {"Зумер", "Зумерский", "Москва, Большой Патриарший пер., 7, строение 1", "Маяковская", "+79966291337", "chrome"},
                {"Зумер", "Зумерский", "Москва, Большой Патриарший пер., 7, строение 1", "Маяковская", "+79966291337", "firefox"}
        };
    }

    // Тест делает заказ самоката по клику на кнопку 'Заказать' в шапке и проверяет наличие модалки об успешном заказе
    @Test
    public void rentScooterHeaderBtn() {
        objMainPage.clickRentScooterHeaderBtn();

        objOrderPage.fillRenterInfoForm(
                renterName, renterLastName, renterScooterDeliveryAddress,
                renterMetroStation, renterPhone
        );
        objOrderPage.clickRentFormContinueBtn();
        objOrderPage.fillAboutRentForm();
        objOrderPage.clickRentScooterOrderFormBtn();
        objOrderPage.clickRentScooterConfirmBtn();

        objOrderPage.waitForScooterOrderSuccessModal();
    }

    // Тест делает заказ самоката по клику на кнопку 'Заказать' в середине страницы и проверяет наличие модалки об успешном заказе
    @Test
    public void rentScooterFooterBtn() {
        objMainPage.clickRentScooterFooterBtn();

        objOrderPage.fillRenterInfoForm(
                renterName, renterLastName, renterScooterDeliveryAddress,
                renterMetroStation, renterPhone
        );
        objOrderPage.clickRentFormContinueBtn();
        objOrderPage.fillAboutRentForm();
        objOrderPage.clickRentScooterOrderFormBtn();
        objOrderPage.clickRentScooterConfirmBtn();

        objOrderPage.waitForScooterOrderSuccessModal();
    }

    // Тест проверяет ошибки для всех полей формы заказа
    @Test
    public void checkScooterOrderFormErrors() {
        objMainPage.clickRentScooterFooterBtn();

        objOrderPage.fillRenterInfoForm(
                "test", "test", "test", "test"
        );

        Assert.assertEquals(
                "Введите корректное имя",
                driver.findElement(objOrderPage.rentScooterNameFieldErrorMessage).getText()
        );
        Assert.assertEquals(
                "Введите корректную фамилию",
                driver.findElement(objOrderPage.rentScooterLastNameFieldErrorMessage).getText()
        );
        Assert.assertEquals(
                "Введите корректный адрес",
                driver.findElement(objOrderPage.rentScooterDeliveryAddressFieldErrorMessage).getText()
        );
        Assert.assertEquals(
                "Введите корректный номер",
                driver.findElement(objOrderPage.rentScooterPhoneFieldErrorMessage).getText()
        );
    }


    @After
    public void quit() {
        driver.quit();
    }

}
