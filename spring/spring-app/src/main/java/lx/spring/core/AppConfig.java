package lx.spring.core;

import lx.spring.core.entities.BaseballGame;
import lx.spring.core.entities.Game;
import lx.spring.core.entities.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by liulixiang on 16/8/9.
 */
@Configuration
@ComponentScan(basePackages = "lx.spring.core")
public class AppConfig {

    @Autowired
    private DataSource dataSource;

    @Resource
    private Team redSox;

    @Resource
    private Team cubs;

    @Bean
    public Game game() {
        BaseballGame game = new BaseballGame(redSox, cubs);
        game.setDataSource(dataSource);
        return game;
    }
}
