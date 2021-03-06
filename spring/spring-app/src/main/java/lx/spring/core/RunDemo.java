package lx.spring.core;

import lx.spring.core.entities.Game;
import lx.spring.core.entities.Team;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.NumberFormat;

/**
 * Created by liulixiang on 16/8/9.
 */
public class RunDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//        NumberFormat nf = context.getBean(NumberFormat.class);
//        double amount = 12345678.9012345;
//        System.out.println(nf.format(amount));

        Team royals = context.getBean("royals", Team.class);
        Team redSox = context.getBean("redSox", Team.class);
        Team cubs = context.getBean("cubs", Team.class);

        Game game1 = context.getBean("game", Game.class);
        System.out.println(game1);
        game1.setHomeTeam(royals);
        game1.setAwayTeam(redSox);
        game1.playGame();
        context.close();
//
//        Game game2 = context.getBean("game", Game.class);
//        game2.setAwayTeam(royals);
//        System.out.println(game2);
//
//        System.out.println(game1);

        //获取Bean的数量
//        System.out.println("Bean的数量" + context.getBeanDefinitionCount());
//        //获取Bean的名称
//        for (String name :
//                context.getBeanDefinitionNames()) {
//            System.out.println(name );
//        }
    }
}
