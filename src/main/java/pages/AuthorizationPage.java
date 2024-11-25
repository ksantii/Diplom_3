package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class AuthorizationPage {

    private final String URL = "https://stellarburgers.nomoreparties.site/login";

    @FindBy(how = How.XPATH, using = ".//a[text()='Зарегистрироваться']")
    private SelenideElement registerLink;
    @FindBy(how = How.XPATH, using = ".//input[@name='name']")
    private SelenideElement emailInput;
    @FindBy(how = How.XPATH, using = "//h2[text()='Вход']")
    private SelenideElement loginHeader;
    @FindBy(how = How.XPATH, using = ".//a[text()='Восстановить пароль']")
    private SelenideElement recoveryPasswordLink;
    @FindBy(how = How.XPATH, using = ".//input[@name='Пароль']")
    private SelenideElement passwordInput;
    @FindBy(how = How.XPATH, using = ".//button[text()='Войти']")
    private SelenideElement loginButton;

    @Step("Открыть страницу входа")
    public AuthorizationPage openAuthorizationPage() {
        open(URL);
        return page(AuthorizationPage.class);
    }

    @Step("Нажать на ссылку 'Зарегистрироваться'")
    public RegistrationPage clickRegisterLink() {
        registerLink.shouldBe(visible).click();
        return Selenide.page(RegistrationPage.class);
    }

    @Step("Установить email: {email}")
    public AuthorizationPage setEmail(String email) {
        emailInput.setValue(email);
        return page(AuthorizationPage.class);
    }

    @Step("Установить пароль")
    public AuthorizationPage setPassword(String password) {
        passwordInput.setValue(password);
        return page(AuthorizationPage.class);
    }

    @Step("Нажать кнопку 'Войти'")
    public void clickLoginButton() {
        loginButton.click();
    }

    @Step("Нажать на ссылку 'Восстановить пароль'")
    public PasswordResetPage clickRecoverPasswordLink() {
        recoveryPasswordLink.click();
        return Selenide.page(PasswordResetPage.class);
    }

    @Step("Войти в систему с email: {email} и паролем")
    public MainPage loginUser(String email, String password) {
        setEmail(email).
                setPassword(password).
                clickLoginButton();
        return Selenide.page(MainPage.class);
    }

    @Step("Ожидаем загрузку страницы входа")
    public AuthorizationPage waitForPageLoad() {
        loginHeader.shouldBe(Condition.visible);
        return page(AuthorizationPage.class);
    }
}