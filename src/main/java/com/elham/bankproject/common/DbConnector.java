package com.elham.bankproject.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnector {
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;
    private static final Logger logger = LogManager.getLogger(DbConnector.class);

    public DbConnector() {
        ConfigLoader loadConfig = new ConfigLoader();
        this.dbUrl = loadConfig.loadConfig("db.url");
        this.dbUser = loadConfig.loadConfig("db.username");
        this.dbPassword = loadConfig.loadConfig("db.password");
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            if (connection != null) {
                logger.info("Connection Ok");
            } else {
                logger.warn("Connection failed");
            }
        } catch (Exception e) {
            logger.error("issue at connection");
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
