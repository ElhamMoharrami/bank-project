package com.elham.bankproject.common;

import com.elham.bankproject.searcher.FileSearcher;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    public ConnectDB() {
        ConfigLoader loadConfig = new ConfigLoader();
        this.dbUrl = loadConfig.loadConfig("db.url");
        this.dbUser = loadConfig.loadConfig("db.username");
        this.dbPassword = loadConfig.loadConfig("db.password");
    }

    public Connection getConnection() {
        Connection connection = null;
        BasicConfigurator.configure();
        Logger log = Logger.getLogger(FileSearcher.class.getName());
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            if (connection != null) {
                log.info("Connection Ok");
            } else {
                log.warn("Connection failed");
            }
        } catch (Exception e) {
            log.error("issue at connection");
        }
        return connection;
    }

}
