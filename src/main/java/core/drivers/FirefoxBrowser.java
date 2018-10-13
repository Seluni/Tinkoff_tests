package core.drivers;

import com.codeborne.selenide.WebDriverProvider;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import java.util.logging.Level;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class FirefoxBrowser implements WebDriverProvider {

    private static FirefoxOptions getFirefoxOptions() {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setLogLevel(FirefoxDriverLogLevel.fromLevel(Level.SEVERE));
        firefoxOptions.addArguments("https://www.tinkoff.ru/");
        firefoxOptions.addArguments("--start-maximized");
        firefoxOptions.addArguments("--disable-web-security");
        firefoxOptions.addArguments("--allow-running-insecure-content");

        capabilities.setCapability(CapabilityType.LOGGING_PREFS, firefoxOptions);

        return firefoxOptions;
    }

    @SuppressWarnings("deprecation")
    @Override
    public WebDriver createDriver(DesiredCapabilities capabilities) {
        FirefoxDriverManager.firefoxdriver().setup();
        FirefoxProfile profile = createFirefoxProfileWithExtensions();
        capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, getFirefoxOptions());
        return new FirefoxDriver(new FirefoxOptions(capabilities).setProfile(profile));
    }

    private FirefoxProfile createFirefoxProfileWithExtensions() {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("plugin.state.flash", 2);

        profile.setPreference("pageLoadStrategy", "normal");

        return profile;
    }
}
