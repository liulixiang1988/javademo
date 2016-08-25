package lx.job.base.entities;

/**
 * 工作类
 * Created by lxliu@iflytek.com on 2016/8/25.
 */
public class WorkClass {
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    private String className;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    private String method;
}
