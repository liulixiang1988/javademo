package lx.job.base.services;

import java.util.List;

/**
 * 作业管理Service
 * Created by lxliu@iflytek.com on 2016/8/25.
 */
public class JobService {

    //region #准备作业内容#

    //进行作业处理之前的准备
    //1.检查不存在计划作业
    //2.检查计划作业是否添加进去
    public void preparing(){

        clearNotExistsJobs();
        prepareBasicJobs();
    }

    private void prepareBasicJobs(){

    }

    private void clearNotExistsJobs(){

    }

    //endregion
}
