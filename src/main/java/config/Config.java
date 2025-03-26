package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;

public class Config {
    private static final Logger log = LoggerFactory.getLogger(Config.class);
    private static Config config = null;
    private final Properties props;

    private Config() {
        this.props = new Properties();

        try{
            props.loadFromXML(getClass().getResourceAsStream("/app.xml"));
        } catch(Exception e) {
            log.error("Error while parsing config: ", e.getMessage());
        }
    }

    public static Config getConfig() {
        if (config == null) {
            config = new Config();
            log.info("Loading config...");
        }

        return config;
    }

    public String get(String key) throws IllegalArgumentException {
        String value = this.props.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Missing config key: " + key);
        }
        return value;
    }

    public int getInt(String key) throws IllegalArgumentException {
        try {
            return Integer.parseInt(this.get(key));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Key " + key + " is not a valid integer.");
        }
    }
}
