package lx.job.base.entities;

/**
 * 作业状态
 * Created by lxliu@iflytek.com on 2016/8/25.
 */
public enum  JobStates {
    UNSTART("UNSTART",0),
    RUNNING("RUNNING",1),
    PAUSED("PAUSED",2),
    STOPPED("STOPPED",3),
    ERROR("ERROR",4);

    private String name;
    private int value;

    JobStates(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "IntellissJobStates{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
