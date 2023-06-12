package com.elham.bankproject.loader;


import com.elham.bankproject.model.Account;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AccountLoader extends TableLoader {
    private final List<String> list;

    public AccountLoader(List<String> list) {
        this.list = list;
    }

    public void loadToTable(List<String> list, PreparedStatement preparedStatement) throws SQLException {
        for (String l : list) {
            String[] value = l.split(",");
            Account account = new Account(Long.parseLong(value[0]), Long.parseLong(value[1]));
            preparedStatement.setLong(1, account.getCustomerId());
            preparedStatement.setLong(2, account.getAccountId());
            preparedStatement.execute();
        }
    }

    public void load() {
        super.executeLoad(list);
    }
}
