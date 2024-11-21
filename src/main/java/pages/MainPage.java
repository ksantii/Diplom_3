package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    public static final String URL = "https://stellarburgers.nomoreparties.site/";

    @FindBy(how = How.XPATH, using = ".//button[text()='Войти в аккаунт']")
    private SelenideElement signIntoAccountButton;
    @FindBy(how = How.XPATH, using = ".//button[text()='Оформить заказ']")
    private SelenideElement orderButton;
    @FindBy(how = How.XPATH, using = ".//span[contains(text(),'Булки')]")
    private SelenideElement buns;
    @FindBy(how = How.XPATH, using = ".//span[contains(text(),'Начинки')]")
    private SelenideElement fillings;
    @FindBy(how = How.CSS, using = ".AppHeader_header__link__3D_hX[href='/']")
    private SelenideElement constructorHeader;
    @FindBy(how = How.XPATH, using = ".//p[text()='Личный Кабинет']")
    private SelenideElement personalAccountButton;
    @FindBy(how = How.XPATH, using = ".//div[@class='AppHeader_header__logo__2D0X2']")
    private SelenideElement burgerLogo;
    @FindBy(how = How.XPATH, using = ".//span[contains(text(),'Соусы')]")
    private SelenideElement sauces;

    @Step("Открыть страницу")
    public MainPage openPage(){
        open(URL);
        return page(MainPage.class);
    }

    @Step("Нажать кнопку 'Войти в аккаунт'")
    public AuthorizationPage clickSignInToAccountButton() {
        signIntoAccountButton.click();
        return page(AuthorizationPage.class);
    }

    @Step("Нажать кнопку 'Личный Кабинет'")
    public AuthorizationPage clickPersonalAccountButton() {
        personalAccountButton.click();
        return page(AuthorizationPage.class);
    }

    @Step("Нажать на логотип бургера")
    public MainPage clickBurgerLogo() {
        burgerLogo.click();
        return page(MainPage.class);
    }

    @Step("Нажать на заголовок конструктора")
    public MainPage clickConstructorHeader() {
        constructorHeader.click();
        return page(MainPage.class);
    }

    @Step("Нажать на Булки")
    public void clickBuns() {
        sauces.click();
        buns.click();
    }

    @Step("Нажать на Соусы")
    public void clickSauces() {
        sauces.click();
    }

    @Step("Нажать на Начинки")
    public void clickFillings() {
        fillings.click();
    }

    @Step("Проверить отображение кнопки 'Оформить заказ'")
    public MainPage orderButtonIsDisplayed() {
        boolean isDisplayed = orderButton.shouldBe(visible, Duration.ofSeconds(5)).isDisplayed();
        Assert.assertTrue("Кнопка оформить заказ не отображается", isDisplayed);
        return page(MainPage.class);
    }

    @Step("Проверить отображение кнопки 'Войти в аккаунт'")
    public MainPage signInToAccountButtonIsDisplayed() {
        boolean isDisplayed = signIntoAccountButton.shouldBe(visible, Duration.ofSeconds(5)).isDisplayed();
        Assert.assertTrue("Кнопка 'Вход в аккаунт' не отображается", isDisplayed);
        return page(MainPage.class);
    }

    @Step("Ожидать загрузку кнопки 'Оформить заказ'")
    public MainPage waitForLoadOrderButton() {
        orderButton.shouldBe(visible);
        return page(MainPage.class);
    }

    @Step("Получить текст активной кнопки")
    public String getTextActionButton() throws InterruptedException {
        Thread.sleep(1000);
        return $(By.className("tab_tab_type_current__2BEPc")).getText();
    }

    @Step("Получить токен доступа")
    public String getAccessToken() {
        return localStorage().getItem("accessToken");
    }
}