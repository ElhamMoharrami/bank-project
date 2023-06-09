package com.elham.bankproject.loader;

import com.elham.bankproject.common.DbConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Statement;

public class TransactionTableCreator implements TableCreator {
    private static final Logger logger = LogManager.getLogger(TransactionTableCreator.class);

    @Override
    public void createTable() {
        DbConnector createConnection = new DbConnector();
        try (Connection connection = createConnection.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS transactions" + "(transaction_id bigint primary key ," +
                    "transaction_time bigint,amount numeric(5,2) ,src_acc bigint,dest_acc bigint," +
                    "transaction_type varchar(7) )");
        } catch (Exception e) {
            logger.error("issue at create transaction table");
        }
    }
}
