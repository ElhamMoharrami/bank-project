package com.elham.bankproject.loader;

import com.elham.bankproject.common.CsvReader;
import com.elham.bankproject.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InsertCustomers extends InsertToTable {

    @Override
    void insert(String files) {
        CsvReader csvReader= new CsvReader();
        List<String> customersList = csvReader.readFile(files + "/customers.csv");
        String INSERT_CUSTOMERS_SQL = "INSERT INTO customers" + "  (customer_id, customer_name, post_address) VALUES " + " (?, ?, ?);";

        try (Connection connection = super.getCreateConnection().getConnection()) {
            PreparedStatement preparedStatementC = connection.prepareStatement(INSERT_CUSTOMERS_SQL);
            connection.setAutoCommit(false);
            for (String c : customersList) {
                String[] value = c.split(",");
                Customer customer = new Customer(value[0], value[1], value[2]);
                preparedStatementC.setString(1, customer.getCustomerId());
                preparedStatementC.setString(2, customer.getName());
                preparedStatementC.setString(3, customer.getAddress());
                preparedStatementC.execute();
            }
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
