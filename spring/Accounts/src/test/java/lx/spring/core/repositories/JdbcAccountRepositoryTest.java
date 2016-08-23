package lx.spring.core.repositories;

import lx.spring.core.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
@ActiveProfiles("test")
public class JdbcAccountRepositoryTest {

    @Autowired
    private AccountRepository repository;

    @Test
    public void getAccounts() throws Exception {

    }

    @Test
    public void getAccount() throws Exception {

    }

    @Test
    public void getNumberOfCounts() throws Exception {

    }

    @Test
    public void createAccount() throws Exception {

    }

    @Test
    public void deleteAccount() throws Exception {

    }

    @Test
    public void updateAccount() throws Exception {

    }

}