package lx.job.base.entities;

import java.util.List;

/**
 * 作业元数据，作业的描述信息
 * Created by lxliu@iflytek.com on 2016/8/25.
 */
public class JobMetadata {
    private String id;
    private String name;
    private JobGroups group;
    private JobStates state;
    private JobCommands cmd;
    private String message;
    // TODO: 2016/8/25 使用泛型
    private Object data;
    private String trigger;//触发器
    private List<WorkClass> workClasses;//工作类
    private String time;
    private boolean isFailed;

    public boolean isFailed() {
        return isFailed;
    }

    public void setFailed(boolean failed) {
        isFailed = failed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JobGroups getGroup() {
        return group;
    }

    public void setGroup(JobGroups group) {
        this.group = group;
    }

    public JobStates getState() {
        return state;
    }

    public void setState(JobStates state) {
        this.state = state;
    }

    public JobCommands getCmd() {
        return cmd;
    }

    public void setCmd(JobCommands cmd) {
        this.cmd = cmd;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WorkClass> getWorkClasses() {
        return workClasses;
    }

    public void setWorkClasses(List<WorkClass> workClasses) {
        this.workClasses = workClasses;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

