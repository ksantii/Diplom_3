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
import pages.RegistrationPage;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
@DisplayName("Регистрация пользователя")
public class RegistrationTest {
    MainPage mainPage = Selenide.page(MainPage.class);
    RegistrationPage registrationPage = Selenide.page(RegistrationPage.class);
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

    public RegistrationTest(String browser) {
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
    @DisplayName("Успешная регистрация пользователя")
    public void registerUserWithValidData() {
        accessToken = mainPage.openPage()
                              .clickSignInToAccountButton()
                              .clickRegisterLink()
                              .registrationUser(user.getName(), user.getEmail(), user.getPassword())
                              .waitForPageLoad()
                              .loginUser(user.getEmail(), user.getPassword())
                              .orderButtonIsDisplayed()
                              .getAccessToken();
    }

    @Test
    @DisplayName("Получить сообщение об ошибке при регистрации пользователя с некорректными паролем")
    public void registerUserWithInvalidPassword() {
        mainPage.openPage()
                .clickSignInToAccountButton()
                .clickRegisterLink()
                .registrationUser(user.getName(), user.getEmail(), User.generateRandomPassword().substring(0, 3));
        registrationPage.verifyInvalidPasswordErrorIsDisplayed();
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
