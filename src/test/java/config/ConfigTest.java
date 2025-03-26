package config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigTest {

    @DisplayName("Validates config creation")
    @Test
    void getConfig_returnsConfig() {
        Config config = Config.getConfig();
        assertNotNull(config);
    }

    @DisplayName("Check that object is the same")
    @Test
    void getConfig_returnsSameObject() {
        Config config1 = Config.getConfig();
        Config config2 = Config.getConfig();
        assertEquals(config1, config2);
    }

    @DisplayName("Returns string if key is found")
    @Test
    void get_returnsString_ifKeyIsFound() {
        Config config = Config.getConfig();
        String host = config.get("server.host");
        assertTrue(host.getClass() == String.class);
    }

    @DisplayName("Returns number is key is found")
    @Test
    void getInt_returnsNumber_ifKeyIsFound() {
        Config config = Config.getConfig();
        Integer port = config.getInt("server.port");
        assertTrue(port.getClass() == Integer.class);
    }
}
