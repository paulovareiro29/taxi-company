package pt.ipvc.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final String PROPERTIES_FILE = "config.properties";
    private static Properties properties;

    public static void setProperties(Properties properties) {
        Config.properties = properties;
    }

    public void load() throws IOException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("config.properties");

        if(stream == null)
            throw new FileNotFoundException("Property file '" + PROPERTIES_FILE +"' not found in the classpath");

        Properties p = new Properties();
        p.load(stream);
        Config.setProperties(p);
    }

    public static int getWindowWidth() {
        return Integer.parseInt(properties.getProperty("window.width"));
    }

    public static int getWindowHeight() {
        return Integer.parseInt(properties.getProperty("window.height"));
    }
}
