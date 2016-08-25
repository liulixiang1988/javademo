package lx.job.app.job;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 管理类Job
 * Created by lxliu@iflytek.com on 2016/8/25.
 */
public class ManagerJob extends BaseJob{

    private static final Logger logger = LoggerFactory.getLogger(ManagerJob.class);

    protected void onExecute(JobExecutionContext jobExecutionContext) throws Exception {
        logger.info("hello");
    }
}
