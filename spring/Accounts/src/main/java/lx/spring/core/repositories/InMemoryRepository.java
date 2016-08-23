package lx.spring.core.repositories;

import lx.spring.core.entities.Account;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by liulixiang on 16/8/14.
 */
public class InMemoryRepository implements AccountRepository {
    @Override
    public List<Account> getAccounts() {
        return null;
    }

    @Override
    public Account getAccount(Long id) {
        return null;
    }

    @Override
    public int getNumberOfCounts() {
        return 0;
    }

    @Override
    public Long createAccount(BigDecimal initialBalance) {
        return null;
    }

    @Override
    public int deleteAccount(Long id) {
        return 0;
    }

    @Override
    public void updateAccount(Account account) {

    }
}
