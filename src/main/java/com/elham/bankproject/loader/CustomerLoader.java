package com.elham.bankproject.loader;

import com.elham.bankproject.common.DbConnector;
import com.elham.bankproject.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CustomerLoader implements Loader {
    @Override
    public void load(List<String> list) {
        String sql = "INSERT INTO customers VALUES (?,?,?)";
        DbConnector createConnection = new DbConnector();
        try (Connection connection = createConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            for (String l : list) {
                String[] value = l.split(",");
                Customer customer = new Customer(Long.parseLong(value[0]), value[1], value[2]);
                preparedStatement.setLong(1, customer.getCustomerId());
                preparedStatement.setString(2, customer.getName());
                preparedStatement.setString(3, customer.getAddress());
                preparedStatement.execute();
            }
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
