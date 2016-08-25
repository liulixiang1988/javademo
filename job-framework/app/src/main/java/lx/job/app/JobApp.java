package lx.job.app;

import lx.job.app.common.AppPath;
import lx.job.app.common.AppProperty;
import lx.job.app.job.ManagerJob;
import lx.job.base.config.RedisConfigurator;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 主方法
 * Created by lxliu@iflytek.com on 2016/8/25.
 */
public class JobApp {
    private static final Logger logger = LoggerFactory.getLogger(JobApp.class);

    public static void main(String[] args) {
        //redis配置
        String redisProperties = AppPath.getAppPropFilePath();
        RedisConfigurator.configure(redisProperties);

        //设置数据库连接配置位置
        System.setProperty("com.mchange.v2.c3p0.cfg.xml", AppPath.getC3p0PropFilePath());

        //配置quartz
        System.setProperty("org.quartz.properties", AppPath.getAppPropFilePath());

        logger.info("JobApp start....");

        try {
            startManagerJob();
        } catch (SchedulerException e) {
            logger.error("JobApp start error ：{}", e.getMessage());
        }
    }

    private static void startManagerJob() throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(ManagerJob.class).withIdentity("job-manager", "manager").build();
        String triggerExpression;
        ScheduleBuilder scheduleBuilder;
        if(AppProperty.getJobManagerTrigger().startsWith("cron:")){
            triggerExpression =AppProperty.getJobManagerTrigger().replace("cron:","");
            scheduleBuilder = CronScheduleBuilder
                    .cronSchedule(triggerExpression);
        }else{
            triggerExpression =AppProperty.getJobManagerTrigger().replace("simple:","");
            int millseconds = Integer.valueOf(triggerExpression);
            scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().repeatForever()
                    .withIntervalInMilliseconds(millseconds);
        }

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("job-manager-trigger", "manager")
                .withPriority(100)
                .startNow()
                .withSchedule(scheduleBuilder).build();

        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scd = sf.getScheduler();
        scd.scheduleJob(jobDetail, trigger);
        scd.start();
    }

}
