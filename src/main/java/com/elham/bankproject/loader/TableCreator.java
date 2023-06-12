package com.elham.bankproject.loader;

import com.elham.bankproject.common.DbConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Statement;

public abstract class TableCreator {
    private static final Logger logger = LogManager.getLogger(TableCreator.class);

    public static TableCreator addTablesToDb(String tableName) {
        switch (tableName) {
            case "accounts":
                return new AccountsTableCreator();
            case "customers":
                return new CustomerTableCreator();
            case "transactions":
                return new TransactionTableCreator();
            default:
                logger.warn("entered table name is not allowed");
        }
        return null;
    }

    public void executeCreate(String query, String tableName) {
        DbConnector createConnection = new DbConnector();
        try (Connection connection = createConnection.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (Exception e) {
            logger.error("issue at create " + tableName + " table");
        }
    }

    abstract void createTable();
}
