package com.elham.bankproject.loader;

import com.elham.bankproject.common.CsvReader;
import com.elham.bankproject.common.DbConnector;
import com.elham.bankproject.model.Account;
import com.elham.bankproject.model.Customer;
import com.elham.bankproject.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TableInsertor {
    public void insert(List<String> list, String tableName, String[] columnNames) {
        String sql = "INSERT INTO " + tableName + " VALUES (";
        for (int i = 0; i < columnNames.length; i++) {
            sql += "?";
            if (i < columnNames.length - 1) {
                sql += ", ";
            }
        }
        sql += ")";
        DbConnector createConnection = new DbConnector();
        try (Connection connection = createConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            for (String l : list) {
                String[] value = l.split(",");
                if (tableName.equals("customers")) {
                    Customer customer = new Customer(value[0], value[1], value[2]);
                    preparedStatement.setString(1, customer.getCustomerId());
                    preparedStatement.setString(2, customer.getName());
                    preparedStatement.setString(3, customer.getAddress());
                    preparedStatement.execute();
                } else if (tableName.equals("accounts")) {
                    Account account = new Account(value[0], value[1]);
                    preparedStatement.setString(1, account.getCustomerId());
                    preparedStatement.setString(2, account.getAccountId());
                    preparedStatement.execute();
                } else if (tableName.equals("transactions")) {
                    Transaction transaction = new Transaction(value[0], Long.parseLong(value[1]),
                            Double.parseDouble(value[2]),
                            value[3], value[4], value[5]);
                    preparedStatement.setString(1, transaction.getId());
                    preparedStatement.setLong(2, transaction.getTime());
                    preparedStatement.setDouble(3, transaction.getAmount());
                    preparedStatement.setString(4, transaction.getAccAId());
                    preparedStatement.setString(5, transaction.getAccBId());
                    preparedStatement.setString(6, transaction.getType());
                    preparedStatement.execute();
                }
            }
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}