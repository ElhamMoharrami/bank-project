package com.elham.bankproject.loader;

import com.elham.bankproject.common.DbConnector;
import com.elham.bankproject.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TransactionLoader implements Loader {
    @Override
    public void load(List<String> list) {
        String sql = "INSERT INTO transactions VALUES (?,?,?,?,?,?)";
        DbConnector createConnection = new DbConnector();
        try (Connection connection = createConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            for (String l : list) {
                String[] value = l.split(",");
                Transaction transaction = new Transaction(Long.parseLong(value[0]), Long.parseLong(value[1]),
                        Double.parseDouble(value[2]),
                        Long.parseLong(value[3]), Long.parseLong(value[4]), value[5]);
                preparedStatement.setLong(1, transaction.getId());
                preparedStatement.setLong(2, transaction.getTime());
                preparedStatement.setDouble(3, transaction.getAmount());
                preparedStatement.setLong(4, transaction.getAccAId());
                preparedStatement.setLong(5, transaction.getAccBId());
                preparedStatement.setString(6, transaction.getType());
                preparedStatement.execute();
            }
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
