package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;

public class PasswordResetPage {

    private final String URL = "https://stellarburgers.nomoreparties.site/forgot-password";

    @FindBy(how = How.XPATH, using = "//h2[text()='Восстановление пароля']")
    private SelenideElement passwordResetHeader;
    @FindBy(how = How.XPATH, using = "//button[text()='Восстановить']")
    private SelenideElement resetButton;
    @FindBy(how = How.CSS, using = "input[name='name']")
    private SelenideElement emailInput;
    @FindBy(how = How.CSS, using = ".Auth_link__1fOlj[href='/login']")
    private SelenideElement loginButton;

    @Step("Открыть страницу восстановления пароля")
    public PasswordResetPage openPasswordResetPage(){
        open(URL);
        return page(PasswordResetPage.class);
    }

    @Step("Установить email для восстановления пароля: {email}")
    public void setEmail(String email) {
        emailInput.setValue(email);
    }

    @Step("Нажать кнопку 'Восстановить'")
    public void clickResetButton() {
        resetButton.click();
    }

    @Step("Нажать кнопку 'Войти'")
    public AuthorizationPage clickLoginButton() {
        loginButton.click();
        return page(AuthorizationPage.class);
    }
}