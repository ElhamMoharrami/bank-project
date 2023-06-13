package com.elham.bankproject.loader;

import com.elham.bankproject.model.Customer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CustomerLoader extends TableLoader {
    private final List<String> list;

    public CustomerLoader(List<String> list) {
        this.list = list;
    }

    public void loadToTable(List<String> list, PreparedStatement preparedStatement) throws SQLException {
        for (String l : list) {
            String[] value = l.split(",");
            Customer customer = new Customer(Long.parseLong(value[0]), value[1], value[2]);
            preparedStatement.setLong(1, customer.getCustomerId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.execute();
        }
    }

    public void load() {
        super.executeLoad(list);
    }

    @Override
    public void run() {
        this.load();
    }
}
