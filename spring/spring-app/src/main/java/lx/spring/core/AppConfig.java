package lx.spring.core;

import lx.spring.core.entities.BaseballGame;
import lx.spring.core.entities.Game;
import lx.spring.core.entities.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

/**
 * Created by liulixiang on 16/8/9.
 */
@Configuration
@ComponentScan(basePackages = "lx.spring.core")
public class AppConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private List<Team> teams;

    @Bean
    //@Scope("prototype")
    public Game game() {
        BaseballGame game = new BaseballGame(teams.get(0), teams.get(1));
        game.setDataSource(dataSource);
        return game;
    }
}
