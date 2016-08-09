package lx.spring.core;

import lx.spring.core.entities.*;

/**
 * Created by liulixiang on 16/8/9.
 */
public class RunDemoWithoutSpring {
    public static void main(String[] args) {
        Team redSox = new RedSox();
        Team cubs = new Cubs();
        Game game = new BaseballGame(redSox, cubs);
        System.out.println(game.playGame());
    }
}
