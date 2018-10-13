package utils;

import static java.util.Optional.ofNullable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.io.IOUtils;

public class AllureEnvironmentUtils {

    public static void create(String browser, String url) {

        FileOutputStream fos = null;

        try {
            Properties props = new Properties();
            fos = new FileOutputStream("build/allure-results/environment.properties");

            ofNullable(browser).ifPresent(s -> props.setProperty("Browser", s));
            ofNullable(url).ifPresent(s -> props.setProperty("Base url", s));
            ofNullable(OsUtils.getOsName())
                .ifPresent(s -> props.setProperty("Operating system", s));

            props.store(fos,
                "See https://github.com/allure-framework/allure-docs/blob/master/docs/features/environment.adoc");

            fos.close();
        } catch (IOException e) {
//            logger.error("IO problem when writing allure properties file", e);
        } finally {
            IOUtils.closeQuietly(fos);
        }
    }

}
