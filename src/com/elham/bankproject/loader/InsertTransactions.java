package com.elham.bankproject.loader;

import com.elham.bankproject.common.CsvReader;
import com.elham.bankproject.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InsertTransactions extends InsertToTable {

    @Override
    void insert(String files) {
        CsvReader csvReader= new CsvReader();
        List<String> transactionList = csvReader.readFile(files + "/transaction.csv");
        String INSERT_TRANSACTIONS_SQL = "INSERT INTO transactions" + "  (transaction_time,amount ,src_acc,dest_acc," +
                "transaction_type ) VALUES " + " (?,?,?,?,?);";
        try (Connection connection = super.getCreateConnection().getConnection()) {
            PreparedStatement preparedStatementT = connection.prepareStatement(INSERT_TRANSACTIONS_SQL);
            connection.setAutoCommit(false);
            for (String t : transactionList) {
                String[] value = t.split(",");
                Transaction transaction = new Transaction(value[0], Long.parseLong(value[1]),
                        Double.parseDouble(value[2]), value[3], value[4], value[5]);
                preparedStatementT.setLong(1, transaction.getTime());
                preparedStatementT.setDouble(2, transaction.getAmount());
                preparedStatementT.setString(3, transaction.getAccAId());
                preparedStatementT.setString(4, transaction.getAccBId());
                preparedStatementT.setString(5, transaction.getType());
                preparedStatementT.execute();
            }
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
