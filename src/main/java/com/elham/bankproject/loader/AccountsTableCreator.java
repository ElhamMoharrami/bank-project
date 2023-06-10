package com.elham.bankproject.loader;

import com.elham.bankproject.common.DbConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Statement;

public class AccountsTableCreator implements TableCreator {
    private static final Logger logger = LogManager.getLogger(AccountsTableCreator.class);

    @Override
    public void createTable() {
        DbConnector createConnection = new DbConnector();
        try (Connection connection = createConnection.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS accounts" + "(customer_id bigint,account_id bigint" +
                    " primary key )");
        } catch (Exception e) {
            logger.error("issue at create account table");
        }
    }
}
