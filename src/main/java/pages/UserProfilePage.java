package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import java.time.Duration;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class UserProfilePage {
    @FindBy(how = How.XPATH, using = ".//button[text()='Выход']")
    private SelenideElement logOutButton;
    @FindBy(how = How.XPATH, using = ".//div[@class='AppHeader_header__logo__2D0X2']")
    private SelenideElement burgerLogo;
    @FindBy(how = How.CSS, using = "a[href='/account/profile']")
    private SelenideElement profileButton;
    @FindBy(how = How.CSS, using = ".AppHeader_header__link__3D_hX[href='/']")
    private SelenideElement constructorHeader;


    @Step("Нажать кнопку 'Выход'")
    public AuthorizationPage logOutButton() {
        logOutButton.click();
        return page(AuthorizationPage.class);
    }

    @Step("Проверить видимость кнопки 'Профиль'")
    public UserProfilePage visibleProfileButton() {
        boolean isDisplayed = profileButton.shouldBe(visible, Duration.ofSeconds(5)).isDisplayed();
        Assert.assertTrue("Кнопка 'Профиль' не отображается", isDisplayed);
        return page(UserProfilePage.class);
    }

    @Step("Нажать на логотип бургера")
    public MainPage clickBurgerLogo() {
        burgerLogo.click();
        return Selenide.page(MainPage.class);
    }

    @Step("Нажать на заголовок конструктора")
    public MainPage clickConstructorHeader() {
        constructorHeader.click();
        return Selenide.page(MainPage.class);
    }

    @Step("Ожидать загрузку страницы")
    public void waitForPageLoad() {
        profileButton.shouldBe(Condition.visible);
    }
}