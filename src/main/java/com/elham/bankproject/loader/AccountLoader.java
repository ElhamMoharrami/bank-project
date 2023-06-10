package com.elham.bankproject.loader;

import com.elham.bankproject.common.DbConnector;
import com.elham.bankproject.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AccountLoader implements Loader {
    @Override
    public void load(List<String> list) {
        String sql = "INSERT INTO accounts VALUES (?,?)";
        DbConnector createConnection = new DbConnector();
        try (Connection connection = createConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            for (String l : list) {
                String[] value = l.split(",");
                Account account = new Account(Long.parseLong(value[0]), Long.parseLong(value[1]));
                preparedStatement.setLong(1, account.getCustomerId());
                preparedStatement.setLong(2, account.getAccountId());
                preparedStatement.execute();
            }
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}