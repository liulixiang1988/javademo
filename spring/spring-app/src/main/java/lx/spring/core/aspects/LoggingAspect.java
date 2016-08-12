package lx.spring.core.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Aspect
@Component //这样使用起来方便
public class LoggingAspect {
    private Logger logger = Logger.getLogger(getClass().getName());
//    @Before("execution(void lx.spring.core..*.set*(*))") //指定Point Cut
//    public void callSetters(JoinPoint joinPoint) {
//        //获取方法名
//        String method = joinPoint.getSignature().getName();
//        //获取参数
//        String args = Arrays.stream(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining(", "));
//        //获取target
//        Object target = joinPoint.getTarget();
//        logger.info("在"+target+"上调用"+method+"参数:"+args);
//    }

//    @Around("execution(String playGame())")
//    public Object checkForRain(ProceedingJoinPoint pjp) throws Throwable {
//        boolean rain = Math.random() < 0.5;
//        Object result = null;
//        if (rain) {
//            logger.info(pjp.getTarget() + " rained out");
//        } else {
//            result = pjp.proceed();
//            logger.info(result.toString());
//        }
//        return result;
//    }
}
