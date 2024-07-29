package br.com.study.ratelimiter.config;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
@Log4j2
public class PropertiesConfigLoader {

    private Properties properties = new Properties();

    private final String propertiesFilePath = System.getenv("PROPERTIES_FILE_PATH"); // Atualize com o caminho do arquivo externo

    public PropertiesConfigLoader() {
        loadConfig();
    }

    private void loadConfig() {
        try (InputStream inputStream = new FileInputStream(propertiesFilePath)) {
            properties.load(inputStream);
            log.info("Properties loaded: " + properties);
        } catch (IOException e) {
            log.error("!! -- falha ao carregar Properties loaded: ", e.getMessage());
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void reloadConfig() {
        loadConfig();
    }
}