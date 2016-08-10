package lx.spring.core;

import lx.spring.core.entities.Game;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by liulixiang on 16/8/9.
 */
public class RunDemo {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Game game = context.getBean("game", Game.class);
        System.out.println(game.playGame());

        //获取Bean的数量
        System.out.println("Bean的数量" + context.getBeanDefinitionCount());
        //获取Bean的名称
        for (String name :
                context.getBeanDefinitionNames()) {
            System.out.println(name );
        }
    }
}
