package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import core.drivers.ChromeBrowser;
import core.drivers.FirefoxBrowser;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import utils.AllureEnvironmentUtils;
import utils.PropertiesResolver;

public abstract class BaseTest {

    private static final String APP_URL = "base.url";
    private static final String TEST_BROWSER = "test.browser";
    private static final String BROWSER_SIZE = "browser.size";

    static {
        String browser = PropertiesResolver.get(TEST_BROWSER);
        //        gradle test -Dtest.browser=chrome
        if (System.getProperty("test.browser") != null) {
            browser = System.getProperty("test.browser");
        } else if (browser.equals("chrome")) {
            browser = ChromeBrowser.class.getName();
        } else if (browser.equals("firefox")) {
            browser = FirefoxBrowser.class.getName();
        } else if (browser.equals("edge")) {
            browser = WebDriverRunner.INTERNET_EXPLORER;
            InternetExplorerDriverManager.getInstance().setup();
        }

//        switch (System.getProperty("test.browser") != null){
//            case (browser.equals("chrome")):
//                browser = ChromeBrowser.class.getName();
//                break;
//            case (browser.equals("firefox")):
//                browser = FirefoxBrowser.class.getName();
//                break;
//            case (browser.equals("edge")):
//                browser = WebDriverRunner.INTERNET_EXPLORER;
//                InternetExplorerDriverManager.getInstance().setup();
//                break;
//            default:
//                browser = System.getProperty("test.browser");
//                break;
//        }

        if (System.getProperty("url") != null) {
            Configuration.baseUrl = System.getProperty("url");
        } else {
            Configuration.baseUrl = PropertiesResolver.get(APP_URL);
        }

        Configuration.browser = browser;
        if (System.getProperty("browser.size") != null) {
            Configuration.browserSize = PropertiesResolver.get(BROWSER_SIZE);
        }
        Configuration.timeout = 10000;
        Configuration.captureJavascriptErrors = true;
    }

    @BeforeAll
    static void setUp() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void afterEach() {
        WebDriverRunner.closeWebDriver();
    }

    @AfterAll
    static void afterAll() {
        AllureEnvironmentUtils.create(Configuration.browser, Configuration.baseUrl);
    }
}
