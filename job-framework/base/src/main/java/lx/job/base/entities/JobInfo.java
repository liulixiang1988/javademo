package lx.job.base.entities;

import org.quartz.JobDetail;
import org.quartz.Trigger;

/**
 * Job信息类
 * Created by liulixiang on 16/8/25.
 */
public class JobInfo {
    private String jobGroup;
    private String jobKey;
    private Trigger trigger;
    private Trigger.TriggerState triggerState = Trigger.TriggerState.NONE;
    private JobDetail jobDetail;

    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }


    public Trigger.TriggerState getTriggerState() {
        return triggerState;
    }

    public void setTriggerState(Trigger.TriggerState triggerState) {
        this.triggerState = triggerState;
    }


    public Trigger getTrigger() {
        return trigger;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    public JobDetail getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(JobDetail jobDetail) {
        this.jobDetail = jobDetail;
    }
}
