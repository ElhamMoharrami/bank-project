package com.elham.bankproject.loader;

import com.elham.bankproject.common.DbConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Statement;

public class CustomerTableCreator implements TableCreator {
    private static final Logger logger = LogManager.getLogger(CustomerTableCreator.class);

    @Override
    public void createTable() {
        DbConnector createConnection = new DbConnector();
        try (Connection connection = createConnection.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS customers" + "(customer_id varchar(10) primary key ," +
                    "customer_name varchar(200) ,post_address varchar(10))");
        } catch (Exception e) {
            logger.error("issue at create customer table");
        }
    }
}
