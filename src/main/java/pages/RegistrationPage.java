package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;

public class RegistrationPage {
    private final String URL = "https://stellarburgers.nomoreparties.site/register";

    @FindBy(how = How.XPATH, using = "//h2[text()='Регистрация']")
    private SelenideElement registerHeader;
    @FindBy(how = How.XPATH, using = "//fieldset[1]/div/div/input")
    private SelenideElement nameInput;
    @FindBy(how = How.XPATH, using = "//fieldset[2]/div/div/input")
    private SelenideElement emailInput;
    @FindBy(how = How.XPATH, using = "//fieldset[3]/div/div/input")
    private SelenideElement passwordInput;
    @FindBy(how = How.XPATH, using = "//button[text()='Зарегистрироваться']")
    private SelenideElement registrationButton;
    @FindBy(how = How.CSS, using = ".Auth_link__1fOlj[href='/login']")
    private SelenideElement loginButton;
    @FindBy(how = How.XPATH, using = "//p[text()='Некорректный пароль']")
    private SelenideElement errorPassword;
    @FindBy(how = How.XPATH, using = "//p[text()='Такой пользователь уже существует']")
    private SelenideElement userExistsError;

    @Step("Открыть страницу регистрации")
    public RegistrationPage openRegistrationPage(){
        open(URL);
        return page(RegistrationPage.class);
    }

    @Step("Установить имя: {name}")
    public RegistrationPage setName(String name) {
        nameInput.setValue(name);
        return page(RegistrationPage.class);
    }

    @Step("Установить email: {email}")
    public RegistrationPage setEmails(String email) {
        emailInput.setValue(email);
        return page(RegistrationPage.class);
    }

    @Step("Установить пароль")
    public RegistrationPage setPassword(String password) {
        passwordInput.setValue(password);
        return page(RegistrationPage.class);
    }

    @Step("Нажать кнопку 'Зарегистрироваться'")
    public AuthorizationPage clickRegistrationButton() {
        registrationButton.click();
        return page(AuthorizationPage.class);
    }

    @Step("Нажать кнопку 'Войти'")
    public AuthorizationPage clickLoginButton() {
        loginButton.click();
        return page(AuthorizationPage.class);
    }

    @Step("Зарегистрировать пользователя с именем: {name}, email: {email}, паролем")
    public AuthorizationPage registrationUser(String name, String email, String password) {
        setName(name).
                setEmails(email).
                setPassword(password).
                clickRegistrationButton();
        return page(AuthorizationPage.class);
    }

    @Step("Проверить отображение ошибки 'Пользователь уже существует'")
    public RegistrationPage verifyUserExistsErrorIsDisplayed() {
        boolean isDisplayed = userExistsError.shouldBe(visible, Duration.ofSeconds(5)).isDisplayed();
        Assert.assertTrue("Ошибка 'Пользователь уже существует' не отображается", isDisplayed);
        return page(RegistrationPage.class);
    }

    @Step("Проверить отображение ошибки 'Некорректный пароль'")
    public RegistrationPage verifyInvalidPasswordErrorIsDisplayed() {
        boolean isDisplayed = errorPassword.shouldBe(visible, Duration.ofSeconds(5)).isDisplayed();
        Assert.assertTrue("Ошибка 'Некорректный пароль' не отображается", isDisplayed);
        return page(RegistrationPage.class);
    }
}