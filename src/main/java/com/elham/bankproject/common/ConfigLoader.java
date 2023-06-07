package com.elham.bankproject.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigLoader {
    private String property;
    private static final Logger logger = LogManager.getLogger(ConfigLoader.class);

    public String loadConfig(String property) {
        try {
            InputStream propsInput = getClass().getResourceAsStream("/config.properties");
            Properties prop = new Properties();
            prop.load(propsInput);
            this.property = prop.getProperty(property);
        } catch (FileNotFoundException e) {
            logger.error("config file not found");
        } catch (IOException e) {
            logger.error("something went wrong");
        }
        return this.property;
    }
}

