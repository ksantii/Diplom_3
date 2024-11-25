package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import api.User;
import api.UserStep;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.MainPage;
import pages.PasswordResetPage;
import pages.UserProfilePage;
import pages.RegistrationPage;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
@DisplayName("Авторизация пользователя")
public class AuthorizationTest {
    MainPage mainPage = Selenide.page(MainPage.class);
    RegistrationPage registrationPage = Selenide.page(RegistrationPage.class);
    PasswordResetPage resetPage = Selenide.page(PasswordResetPage.class);
    UserProfilePage profilePage = Selenide.page(UserProfilePage.class);
    UserStep userStep;
    User user;
    String accessToken;
    private final String browser;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "chrome" },
                { "yandex" }
        });
    }

    public AuthorizationTest(String browser) {
        this.browser = browser;
    }

    @Before
    public void setUp() {
        setBrowser(browser);
        user = new User(
                User.generateRandomEmail(),
                User.generateRandomPassword(),
                User.generateRandomName()
        );
        userStep = new UserStep(user);
        accessToken = userStep.createUser().then().extract().body().path("accessToken");
    }

    @Step("Настройка браузера: {browser}")
    private void setBrowser(String browser) {
        if ("yandex".equals(browser)) {
            System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\yandex\\yandexdriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.setBinary("C:\\Users\\User\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");
            Configuration.browserCapabilities = options;
            Configuration.browser = "chrome";
        } else {
            Configuration.browser = browser;
        }
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной странице")
    public void loginFromMainPage() {
        mainPage.openPage()
                .clickSignInToAccountButton()
                .loginUser(user.getEmail(), user.getPassword())
                .orderButtonIsDisplayed();
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет' на главной странице")
    public void loginThroughPersonalAccountButtonOnMainPage() {
        mainPage.openPage()
                .clickPersonalAccountButton()
                .loginUser(user.getEmail(), user.getPassword())
                .orderButtonIsDisplayed();
    }

    @Test
    @DisplayName("Вход со страницы регистрации")
    public void loginFromRegistrationPage() {
        registrationPage.openRegistrationPage()
                        .clickLoginButton()
                        .loginUser(user.getEmail(), user.getPassword())
                        .orderButtonIsDisplayed();
    }

    @Test
    @DisplayName("Вход со страницы восстановления пароля")
    public void loginFromPasswordResetPage() {
        resetPage.openPasswordResetPage()
                 .clickLoginButton()
                 .loginUser(user.getEmail(), user.getPassword())
                 .orderButtonIsDisplayed();
    }

    @Test
    @DisplayName("Вход в личный кабинет")
    public void navigateToAccount() {
        mainPage.openPage()
                .clickSignInToAccountButton()
                .loginUser(user.getEmail(), user.getPassword())
                .clickPersonalAccountButton();
        profilePage.visibleProfileButton();
    }

    @Test
    @DisplayName("Выход из личного кабинета")
    public void logoutFromAccount() {
        mainPage.openPage()
                .clickSignInToAccountButton()
                .loginUser(user.getEmail(), user.getPassword())
                .clickPersonalAccountButton();
        profilePage.logOutButton().waitForPageLoad();
    }

    @Test
    @DisplayName("Переход из личного кабинета, кликнув на Конструктор")
    public void navigateToMainPageByConstructorHeader() {
        mainPage.openPage()
                .clickSignInToAccountButton()
                .loginUser(user.getEmail(), user.getPassword())
                .clickPersonalAccountButton();
        profilePage.clickConstructorHeader().orderButtonIsDisplayed();
    }

    @Test
    @DisplayName("Переход из личного кабинета, кликнув на логотип Stellar Burgers")
    public void navigateToMainPageByBurgerLogo() {
        mainPage.openPage()
                .clickSignInToAccountButton()
                .loginUser(user.getEmail(), user.getPassword())
                .clickPersonalAccountButton();
        profilePage.clickBurgerLogo().orderButtonIsDisplayed();
    }

    @After
    public void tearDown() {
        Selenide.closeWebDriver();
        if (accessToken != null) {
            userStep.deleteUser(accessToken);
            accessToken = null;
        }
    }
}
