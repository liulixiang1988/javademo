package lx.spring.core.entities;

/**
 * Created by liulixiang on 16/8/9.
 */
public interface Game {
    void setHomeTeam(Team team);
    Team getHomeTeam();
    void setAwayTeam(Team team);
    Team getAwayTeam();
    String playGame();
}
