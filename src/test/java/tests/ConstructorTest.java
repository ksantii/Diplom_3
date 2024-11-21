package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.MainPage;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
@DisplayName("Конструктор бургеров")
public class ConstructorTest {
    MainPage mainPage = Selenide.page(MainPage.class);
    private final String browser;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "chrome" },
                { "yandex" }
        });
    }

    public ConstructorTest(String browser) {
        this.browser = browser;
    }

    @Before
    public void setUp() {
        setBrowser(browser);
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
    @DisplayName("Переход к разделу 'Булки'")
    public void verifyBunsSection() throws InterruptedException {
        mainPage.openPage()
                .clickBuns();
        assertEquals("Булки", mainPage.getTextActionButton());
    }

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    public void verifySaucesSection() throws InterruptedException {
        mainPage.openPage()
                .clickSauces();
        assertEquals("Соусы", mainPage.getTextActionButton());
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    public void verifyFillingsSection() throws InterruptedException {
        mainPage.openPage()
                .clickFillings();
        assertEquals("Начинки", mainPage.getTextActionButton());
    }

    @After
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}
