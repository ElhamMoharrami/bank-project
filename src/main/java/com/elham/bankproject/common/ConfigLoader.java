package com.elham.bankproject.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigLoader {
    private String property;
    private static String configLoc;
    private static final Logger logger = LogManager.getLogger(ConfigLoader.class);

    public String loadConfig(String property) {
        try {
            Path configFilePath = Paths.get(configLoc + "/config.properties");
            FileInputStream propsInput = new FileInputStream(String.valueOf(configFilePath));
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

//    public String getProperty() {
//        return property;
//    }

    public void setConfigLoc(String configLoc) {
        ConfigLoader.configLoc = configLoc;
    }
}

