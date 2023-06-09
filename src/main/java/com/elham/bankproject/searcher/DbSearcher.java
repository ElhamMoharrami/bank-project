package com.elham.bankproject.searcher;

import com.elham.bankproject.common.DbConnector;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbSearcher implements Searcher {
    private final DbConnector createConnection = new DbConnector();
    private List<CustomerTransaction> result;
    private static final Logger logger = LogManager.getLogger(DbSearcher.class);
    private final static String SEARCH_CUSTOMER_TRANSACTION_SQL = "SELECT customer_name,customers.customer_id,transaction_time," +
            "amount,src_acc,dest_acc,transaction_type \n" +
            "FROM customers INNER JOIN accounts ON customers.customer_id=accounts.customer_id INNER JOIN transactions " +
            " ON accounts.account_id=transactions.src_acc OR accounts.account_id=transactions.dest_acc  WHERE customer_name=?";

    @Override
    public List<CustomerTransaction> search(String keyword) {
        try (Connection connection = createConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_CUSTOMER_TRANSACTION_SQL);
            this.result = new ArrayList<>();
            preparedStatement.setString(1, keyword);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CustomerTransaction searchByName = new CustomerTransaction(
                        resultSet.getString("customer_name"),
                        resultSet.getLong("customer_id"),
                        resultSet.getLong("transaction_time"),
                        resultSet.getString("amount"),
                        resultSet.getLong("src_acc"),
                        resultSet.getLong("dest_acc"),
                        resultSet.getString("transaction_type"));
                result.add(searchByName);
            }
        } catch (SQLException throwables) {
            logger.warn(throwables.getMessage());
        }
        return result;
    }
}
