package core.drivers;

import com.codeborne.selenide.WebDriverProvider;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class RemoteDriver implements WebDriverProvider {

    public static URL getGridHubUrl() {
        URL hostURL = null;
        try {
            hostURL = new URL("http://127.0.0.1:4444/wd/hub");
        } catch (MalformedURLException e) {
            //e.printStackTrace();
        }
        return hostURL;
    }

    @SuppressWarnings("deprecation")
    @Override
    public WebDriver createDriver(DesiredCapabilities capabilities) {
        capabilities.setBrowserName("chrome");
        capabilities.setCapability(ChromeOptions.CAPABILITY, ChromeBrowser.getChromeOptions());

        capabilities.setCapability("screenResolution", "1920x1080x24");
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("timeZone", "America/New_York");

        //System.out.println("video.enabled: " + System.getProperty("video.enabled"));
        if ("true".equals(System.getProperty("video.enabled"))) {
            capabilities.setCapability("enableVideo", true);
            capabilities.setCapability("videoFrameRate", 24);
        }

        return new org.openqa.selenium.remote.RemoteWebDriver(getGridHubUrl(), capabilities);
    }

}
