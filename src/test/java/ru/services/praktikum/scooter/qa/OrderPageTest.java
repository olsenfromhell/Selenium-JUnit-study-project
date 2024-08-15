package ru.services.praktikum.scooter.qa;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.services.praktikum.DriverRule;

@RunWith(Parameterized.class)
public class OrderPageTest extends OrderPage {
    private WebDriver driver;

    @Rule
    public DriverRule factory = new DriverRule();

    public OrderPageTest(
            String renterName, String renterLastName, String renterScooterDeliveryAddress,
            String renterMetroStation, String renterPhone
    ) {
        super.renterName = renterName;
        super.renterLastName = renterLastName;
        super.renterScooterDeliveryAddress = renterScooterDeliveryAddress;
        super.renterMetroStation = renterMetroStation;
        super.renterPhone = renterPhone;

    }

    @Parameterized.Parameters
    public static Object[][] getRentalFormData() {
        return new Object[][] {
                {"Зумер", "Зумерский", "Москва, Большой Патриарший пер., 7, строение 1", "Маяковская", "+79966291337" },
        };
    }

    // Тест делает заказ самоката по клику на кнопку 'Заказать' в шапке и проверяет наличие модалки об успешном заказе
    @Test
    public void rentScooterHeaderBtn() {
        WebDriver driver = factory.getDriver();
        MainPage objMainPage = new MainPage(driver);
        OrderPage objOrderPage = new OrderPage(driver);

        objMainPage.openMainPage();
        objMainPage.clickAcceptCookieBtn();
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
        WebDriver driver = factory.getDriver();
        MainPage objMainPage = new MainPage(driver);
        OrderPage objOrderPage = new OrderPage(driver);

        objMainPage.openMainPage();
        objMainPage.clickAcceptCookieBtn();
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
        WebDriver driver = factory.getDriver();
        MainPage objMainPage = new MainPage(driver);
        OrderPage objOrderPage = new OrderPage(driver);

        objMainPage.openMainPage();
        objMainPage.clickAcceptCookieBtn();
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
}