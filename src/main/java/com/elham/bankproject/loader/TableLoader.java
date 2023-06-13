package com.elham.bankproject.loader;

import com.elham.bankproject.common.DbConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class TableLoader implements Runnable {
    private String sql;
    private static final Logger logger = LogManager.getLogger(TableLoader.class);

    public static TableLoader loadToDb(String tableName, List<String> list) {
        switch (tableName) {
            case "accounts":
                return new AccountLoader(list);
            case "customers":
                return new CustomerLoader(list);
            case "transactions":
                return new TransactionLoader(list);
            default:
                logger.warn("entered table name is not allowed");
        }

        return null;
    }

    protected void executeLoad(List<String> list) {
        DbConnector createConnection = new DbConnector();
        try (Connection connection = createConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(this.sql);
            connection.setAutoCommit(false);
            loadToTable(list, preparedStatement);
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setSql(String tableName) {
        switch (tableName) {
            case "accounts":
                this.sql = "INSERT INTO accounts VALUES (?,?)";
                break;
            case "customers":
                this.sql = "INSERT INTO customers VALUES (?,?,?)";
                break;
            case "transactions":
                this.sql = "INSERT INTO transactions VALUES (?,?,?,?,?,?)";
                break;
            default:
                logger.warn("entered table name is not allowed");
        }
    }

    abstract void loadToTable(List<String> list, PreparedStatement preparedStatement) throws SQLException;

    abstract void load();
}
