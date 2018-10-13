package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * The type Properties resolver.
 */
public class PropertiesResolver {

    private static Properties props;

    /**
     * Get string.
     *
     * @param key the key
     * @return the string
     */
    public static String get(String key) {
        if (props == null) {
            props = new Properties();

            InputStream inputStream = PropertiesResolver.class.getClassLoader()
                .getResourceAsStream("config.properties");

            try {
                props.load(new InputStreamReader(inputStream, "UTF-8"));
            } catch (IOException ex) {
                // Skip it, UTF-8 should always be available
            }
        }
        return props.getProperty(key);
    }
}
