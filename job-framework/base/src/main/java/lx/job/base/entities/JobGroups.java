package lx.job.base.entities;

/**
 * 作业组别
 * Created by lxliu@iflytek.com on 2016/8/25.
 */
public enum JobGroups {
    BASIC("BASIC",0),
    RESULT("RESULT", 1);

    private String name;
    private int value;

    JobGroups(String name, int value) {
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
