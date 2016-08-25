package lx.job.base.entities;

/**
 * 作业命令
 * Created by lxliu@iflytek.com on 2016/8/25.
 */
public enum JobCommands{

    NULL("NULL",-1),
    START("START",0),
    PAUSE("PAUSE",1),
    RESUME("RESUME",2),
    STOP("STOP",3),
    DESTORY("DESTORY",4),
    UPDATE_TRIGGER("UPDATETRIGGER",5),
    UPDATE_DATA("UPDATE_DATA",6);

    private String name;
    private int value;

    JobCommands(String name, int value) {
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