package com.elham.bankproject.common;

import com.elham.bankproject.searcher.FileSearcher;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigLoader {
    private String property;
    private static String configLoc;

    public String loadConfig(String property) {
        BasicConfigurator.configure();
        Logger log = Logger.getLogger(FileSearcher.class.getName());
        try {
            Path configFilePath = Paths.get(configLoc + "/config.properties");
            FileInputStream propsInput = new FileInputStream(String.valueOf(configFilePath));
            Properties prop = new Properties();
            prop.load(propsInput);
            this.property = prop.getProperty(property);
        } catch (FileNotFoundException e) {
            log.error("config file not found");
        } catch (IOException e) {
            log.error("something went wrong");
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

