package lx.job.base.utils;

import lx.job.base.entities.JobInfo;
import lx.job.base.entities.JobMetadata;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调度工具类
 * Created by liulixiang on 16/8/25.
 */
@Component
public class QuartzUtils {
    private static final Logger logger = LoggerFactory.getLogger(QuartzUtils.class);

    /**
     * 获取所有在运行的Job
     * @return
     * @throws Exception
     */
    public static List<JobInfo> getAllJobs() throws Exception {
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        List<JobInfo> jobInfos = new ArrayList<>();

        for (String groupName : scheduler.getJobGroupNames()) {
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                JobInfo jobInfo = new JobInfo();
                jobInfo.setJobKey(jobKey.getName());
                jobInfo.setJobGroup(jobKey.getGroup());
                List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                if(triggers!=null&&triggers.size()>0) {
                    jobInfo.setTrigger(triggers.get(0));
                    jobInfo.setTriggerState(scheduler.getTriggerState(triggers.get(0).getKey()));
                    jobInfo.setJobDetail(scheduler.getJobDetail(jobKey));
                }
                jobInfos.add(jobInfo);
            }
        }
        return jobInfos;
    }

    /**
     * 根据Group获取作业
     * @param groupName
     * @return
     * @throws Exception
     */
    public static Map<String,JobInfo> getJobsByGroup(Object groupName) throws Exception {
        String groupKey = "group_" + groupName;
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        Map<String,JobInfo> jobInfos = new HashMap<>();
        for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupKey))) {
            JobInfo jobInfo = new JobInfo();
            jobInfo.setJobKey(jobKey.getName());
            jobInfo.setJobGroup(jobKey.getGroup());
            List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
            if(triggers!=null&&triggers.size()>0) {
                jobInfo.setTrigger(triggers.get(0));
                jobInfo.setTriggerState(scheduler.getTriggerState(triggers.get(0).getKey()));
                jobInfo.setJobDetail(scheduler.getJobDetail(jobKey));
            }

            jobInfos.put(jobInfo.getJobKey(),jobInfo);
        }
        return jobInfos;
    }

    public static void createJob(String groupName, JobMetadata jobMetadata, Class<? extends Job> cls) throws SchedulerException {

        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scd = sf.getScheduler();

        String groupKey = "group_" + groupName;
        String strJobKey = "job_" + jobMetadata.getId();
        String strTriggerKey = "trigger_" + jobMetadata.getId();

        JobKey jobKey =JobKey.jobKey(strJobKey,groupKey);
        TriggerKey triggerKey = TriggerKey.triggerKey(strTriggerKey,groupKey);
        if(scd.checkExists(jobKey)){
            scd.pauseTrigger(triggerKey);
            scd.unscheduleJob(triggerKey);
            scd.deleteJob(jobKey);
        }

        JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(strJobKey, groupKey).build();
        jobDetail.getJobDataMap().put("metadata",jobMetadata);

        ScheduleBuilder scheduleBuilder;
        String triggerExpression;
        String configTriggerExpression = jobMetadata.getTrigger();
        if(configTriggerExpression.startsWith("cron:")) {
            triggerExpression = configTriggerExpression.replace("cron:","");
            scheduleBuilder = CronScheduleBuilder
                    .cronSchedule(triggerExpression);

        } else {
            triggerExpression = configTriggerExpression.trim().replace("simple:","");
            int millseconds = Integer.valueOf(triggerExpression);
            scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().repeatForever()
                    .withIntervalInMilliseconds(millseconds);
        }

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(strTriggerKey, groupKey)
                .startNow()
                .withSchedule(scheduleBuilder).build();

        scd.scheduleJob(jobDetail, trigger);
        scd.start();
    }

    public static void pauseJob(String groupName, JobMetadata jobMetadata) throws  SchedulerException{
        String groupKey = "group_"+groupName;
        String jobId = "job_"+jobMetadata.getId();
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        JobKey jobkey = JobKey.jobKey(jobId,groupKey);
        if(jobkey!=null)
            scheduler.pauseJob(jobkey);
    }

    public static void resumeJob(String groupName, JobMetadata jobMetadata) throws  SchedulerException{
        String groupKey = "group_"+groupName;
        String jobId = "job_"+jobMetadata.getId();
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        JobKey jobkey = JobKey.jobKey(jobId,groupKey);
        if(jobkey!=null)
            scheduler.resumeJob(jobkey);
    }

    public static void stopJob(String groupName, JobMetadata jobMetadata) throws  SchedulerException{
        String groupKey = "group_"+groupName;
        String jobId = "job_"+jobMetadata.getId();
        String triggerId ="trigger_"+jobMetadata.getId();
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        JobKey jobkey = JobKey.jobKey(jobId,groupKey);
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerId,groupKey);

        if(jobkey!=null) {
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(jobkey);
        }
    }

    public static void stopJob(JobInfo job) throws  SchedulerException{
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        JobKey jobkey = JobKey.jobKey(job.getJobKey(),job.getJobGroup());
        TriggerKey triggerKey = job.getTrigger().getKey();
        scheduler.pauseTrigger(triggerKey);
        scheduler.unscheduleJob(triggerKey);
        scheduler.deleteJob(jobkey);
    }

    public static void updateJobTrigger(String groupName, JobMetadata jobMetadata) throws  SchedulerException{
        String groupKey = "group_"+groupName;
        String triggerId = "trigger_"+jobMetadata.getId();
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerId,groupKey);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if(trigger!=null){

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobMetadata.getTrigger());

            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder).build();

            //按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    public static void updateJobDatamap(String groupName, JobMetadata jobMetadata) throws  SchedulerException{
        String groupKey = "group_"+groupName;
        String jobId = "job_"+jobMetadata.getId();
        TriggerKey triggerKey= TriggerKey.triggerKey("trigger_"+jobMetadata.getId());
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        JobKey jobkey = JobKey.jobKey(jobId,groupKey);
        if(jobkey!=null) {
            JobDetail jobDetail = scheduler.getJobDetail(jobkey);
            jobDetail.getJobDataMap().put("metadata",jobMetadata);
        }
    }
}
