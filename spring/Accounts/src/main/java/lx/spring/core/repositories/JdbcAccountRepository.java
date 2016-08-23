package lx.spring.core.repositories;


import lx.spring.core.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Profile("test")
public class JdbcAccountRepository implements AccountRepository {

    private JdbcTemplate template;
    private static long nextId = 4;

    @Autowired
    public JdbcAccountRepository(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Account> getAccounts() {
        String sqlText = "SELECT * FROM account";
        return template.query(sqlText, new AccountMapper());
    }

    @Override
    public Account getAccount(Long id) {
        String sqlText = "SELECT * FROM account WHERE id=?";
        return template.queryForObject(sqlText, new AccountMapper(), id);
    }

    @Override
    public int getNumberOfCounts() {
        String sqlText = "SELECT count(*) FROM account";
        return template.queryForObject(sqlText, Integer.class);
    }

    @Override
    public Long createAccount(BigDecimal initialBalance) {
        String sqlText = "insert into account(id, balance) values(?, ?)";
        long id = nextId ++;
        int uc = template.update(sqlText, id, initialBalance);
        return id;
    }

    @Override
    public int deleteAccount(Long id) {
        String sqlText = "delete from account where id=?";
        return template.update(sqlText, id);
    }

    @Override
    public void updateAccount(Account account) {
        String sqlText = "update account set balance=? where id = ?";
        template.update(sqlText, account.getBalance(), account.getId());
    }

    private class AccountMapper implements RowMapper<Account>{
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Account(rs.getLong("id"), rs.getBigDecimal("balance"));
        }
    }
}
