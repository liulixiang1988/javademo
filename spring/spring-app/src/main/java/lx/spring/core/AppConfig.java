package lx.spring.core;

import lx.spring.core.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by liulixiang on 16/8/9.
 */
@Configuration
@ComponentScan(basePackages = "lx.spring.core")
public class AppConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired @Qualifier("redSox")
    private Team home;


    @Autowired @Qualifier("cubs")
    private Team away;

    @Bean
    public Game game() {
        BaseballGame game = new BaseballGame(home, away);
        game.setDataSource(dataSource);
        return game;
    }
}
