package lx.job.app.job;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 基础Job
 * Created by lxliu@iflytek.com on 2016/8/25.
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public abstract class BaseJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(BaseJob.class);


    private java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final String TIME_KEY = "TIME";
    public static final String MSG_KEY = "MESSAGE";
    public static final String FAIL_KEY="ISFAILED";

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap =jobExecutionContext.getJobDetail().getJobDataMap();
        dataMap.put(TIME_KEY,dateFormat.format(new Date()));
        try{
            onBeforeExecute(jobExecutionContext);
            onExecute(jobExecutionContext);
            onAfterExecute(jobExecutionContext);


        }catch (Exception e){
            logger.error("run job:{} raise {} Exception : {}",e.getClass().toString(),e.getMessage(),e);
            dataMap.put(MSG_KEY,e.getMessage());
        }
    }

    protected void onBeforeExecute(JobExecutionContext jobExecutionContext){
        logger.debug("baseJob.onBeforeExecute 执行了。");
    }

    protected abstract void onExecute(JobExecutionContext jobExecutionContext) throws Exception;

    protected void onAfterExecute(JobExecutionContext jobExecutionContext){

    }
}
