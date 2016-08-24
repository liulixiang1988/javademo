package lx.spring.core.services;

import lx.spring.core.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
public class AccountServiceTest {

    @Autowired
    private AccountService service;

    @Test
    public void deposit() throws Exception {
        BigDecimal start = service.getBalance(1L);
        BigDecimal amount = new BigDecimal("50.0");

        service.deposit(1L, amount);
        BigDecimal finish = start.add(amount);

        assertThat(finish, is(closeTo(service.getBalance(1L), new BigDecimal("0.01"))));
    }

    @Test
    public void withdraw() throws Exception {
        BigDecimal start = service.getBalance(1L);
        BigDecimal amount = new BigDecimal("50.0");

        service.withdraw(1L, amount);
        BigDecimal finish = start.subtract(amount);

        assertThat(finish, is(closeTo(service.getBalance(1L), new BigDecimal("0.01"))));
    }

    @Test
    public void transfer() throws Exception {
        BigDecimal acct1start = service.getBalance(1L);
        BigDecimal acct2start = service.getBalance(2L);

        BigDecimal amount = new BigDecimal("50.0");
        service.transfer(1L, 2L, amount);
        BigDecimal acct1finish = acct1start.subtract(amount);
        BigDecimal acct2finish = acct2start.add(amount);

        assertThat(acct1finish, is(closeTo(service.getBalance(1L), new BigDecimal("0.01"))));
        assertThat(acct2finish, is(closeTo(service.getBalance(2L), new BigDecimal("0.01"))));
    }
}