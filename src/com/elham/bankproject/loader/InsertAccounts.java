package com.elham.bankproject.loader;

import com.elham.bankproject.common.CsvReader;
import com.elham.bankproject.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InsertAccounts extends InsertToTable {

    @Override
    void insert(String files) {
        CsvReader csvReader= new CsvReader();
        List<String> accountsList = csvReader.readFile(files + "/accounts.csv");
        String INSERT_ACCOUNTS_SQL = "INSERT INTO accounts" + "  (customer_id, account_id) VALUES " + " (?, ?);";

        try (Connection connection = super.getCreateConnection().getConnection()) {
            PreparedStatement preparedStatementC = connection.prepareStatement(INSERT_ACCOUNTS_SQL);
            connection.setAutoCommit(false);
            for (String a : accountsList) {
                String[] value = a.split(",");
                Account account = new Account(value[0], value[1]);
                preparedStatementC.setString(1, account.getCustomerId());
                preparedStatementC.setString(2, account.getAccountId());
                preparedStatementC.execute();
            }
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
