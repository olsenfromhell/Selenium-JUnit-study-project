package ru.services.praktikum.scooter.qa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TrackingPage extends MainPage {
    // Картинка "Not found" на странице поиска заказа
    protected final By orderNotFoundImage = By.cssSelector("img[alt='Not found']");


    public TrackingPage(WebDriver driver) {
    }
}
