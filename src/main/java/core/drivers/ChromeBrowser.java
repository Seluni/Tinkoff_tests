package core.drivers;

import com.codeborne.selenide.WebDriverProvider;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class ChromeBrowser implements WebDriverProvider {

    public static ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.addArguments("user-data-dir=./src/test/profiles/chrome/testProfile/");
        chromeOptions.addArguments("--start-maximized");
        //chromeOptions.addArguments("disable-popup-blocking", "true");
        chromeOptions.addArguments("disable-infobars");
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        prefs.put("profile.default_content_setting_values.plugins", 1);
        prefs.put("profile.content_settings.plugin_whitelist.adobe-flash-player", 1);
        prefs.put("profile.content_settings.exceptions.plugins.*,*.per_resource.adobe-flash-player",
            1);
        prefs.put("PluginsAllowedForUrls", "https://default.kadenastage.com/*");

        prefs.put("pageLoadStrategy", "normal");

        chromeOptions.setExperimentalOption("prefs", prefs);

        return chromeOptions;
    }

    @SuppressWarnings("deprecation")
    @Override
    public WebDriver createDriver(DesiredCapabilities capabilities) {
        ChromeDriverManager.chromedriver().version("2.39").setup();
        capabilities.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());
        return new ChromeDriver(capabilities);
    }

//    private DesiredCapabilities getCapabilitiesWithCustomFileDownloadFolder(DesiredCapabilities capabilities) {
//        Map<String, Object> preferences = new Hashtable<>();
//        preferences.put("profile.default_content_settings.popups", 0);
//        preferences.put("download.prompt_for_download", "false");
//        String downloadsPath = System.getProperty("user.home") + "/Downloads";
//        preferences.put("download.default_directory", loadSystemPropertyOrDefault("fileDownloadPath", downloadsPath));
//        preferences.put("plugins.plugins_disabled", new String[]{
//            "Adobe Flash Player", "Chrome PDF Viewer"});
//        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//
//        ChromeOptions options = new ChromeOptions();
//        options.setExperimentalOption("prefs", preferences);
//
//        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
//        return capabilities;
//    }
//
//    public static String loadSystemPropertyOrDefault(String propertyName, String defaultValue) {
//        String propValue = System.getProperty(propertyName);
//        return propValue != null ? propValue : defaultValue;
//    }

}
