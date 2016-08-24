package lx.spring.core.repositories;

import lx.spring.core.config.AppConfig;
import lx.spring.core.entities.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.number.BigDecimalCloseTo.closeTo;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
@Transactional
public class JpaAccountRepositoryTest {
    @Autowired
    private AccountRepository repository;

    @Test
    public void getAccounts() throws Exception {
        List<Account> accounts = repository.getAccounts();
        assertThat(accounts.size(), is(3));
    }

    @Test
    public void getNumberOfAccounts() throws Exception {
        assertThat(repository.getNumberOfAccounts(), is(3));
    }

    @Test
    public void getAccount() throws Exception {
        Account account = repository.getAccount(1L);
        assertThat(account.getId(), is(1L));
        //2. 近似
        assertThat(new BigDecimal("100.0"), is(closeTo(account.getBalance(), new BigDecimal("0.01"))));
    }

    @Test
    public void createAccount() throws Exception {
        Long id = repository.createAccount(new BigDecimal("250.00"));
        //3. 非空
        assertThat(id, is(notNullValue()));

        Account account = repository.getAccount(id);
        assertThat(account.getId(), is(id));
        assertThat(new BigDecimal("250.0"), is(closeTo(account.getBalance(), new BigDecimal("0.01"))));
    }

    @Test
    public void updateAccount() throws Exception {
        Account account = repository.getAccount(1L);
        BigDecimal current = account.getBalance();
        BigDecimal amount = new BigDecimal("50.0");
        account.setBalance(current.add(amount));
        repository.updateAccount(account);

        Account again = repository.getAccount(1L);
        assertThat(again.getBalance(), is(closeTo(account.getBalance(), new BigDecimal("0.01"))));
    }

    @Test
    public void deleteAccount() throws Exception {
        for (Account account: repository.getAccounts()) {
            repository.deleteAccount(account.getId());
        }
        assertThat(repository.getNumberOfAccounts(), is(0));
    }

}