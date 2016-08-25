package lx.job.base.services;

import com.google.gson.reflect.TypeToken;
import lx.job.base.entities.*;
import lx.job.base.utils.JsonUtils;
import lx.job.base.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by lxliu@iflytek.com on 2016/8/25.
 */
public class JobMetadataService {
    private static final String DEFAULT_JOB_FILE = "default.jobs.json";
    private static final Logger logger = LoggerFactory.getLogger(JobMetadataService.class);

    private List<JobMetadata> defaultJobList = null;

    //为指定计划，添加默认jobs
    public void addDefaultBasicJobs(String jobCategoryKey, Object data) {

        try {
            //0.找到配置文件
            String defaultJobFile = System.getProperty(DEFAULT_JOB_FILE);
            if (defaultJobFile.isEmpty()) {
                logger.error("JobMetadataService.addDefaultBasicJobs error: System.property:{} is not defined.", DEFAULT_JOB_FILE);
                return;
            }

            //1.读取
            if (defaultJobList == null || defaultJobList.size() == 0) {
                String defaultJobJSON = Files.readAllLines(Paths.get(defaultJobFile)).stream().collect(Collectors.joining());
                if (defaultJobJSON.isEmpty()) {
                    logger.error("JobMetadataService.addDefaultBasicJobs error: defaultJobJSON is null.");
                    return;
                }
                logger.debug("JobMetadataService.addDefaultBasicJobs defaultjob:{}.", defaultJobJSON);

                Type listType = new TypeToken<List<JobMetadata>>() {
                }.getType();
                defaultJobList = JsonUtils.toObject(defaultJobJSON, listType);
                if (defaultJobList == null) {
                    logger.error("JobMetadataService.addDefaultBasicJobs error: parse defaultJobJSON error.");
                    return;
                }
            }

            //2.添加自己的一些属性
            for (JobMetadata job : defaultJobList) {
                job.setData(data);
                job.setCmd(JobCommands.START);
                job.setGroup(JobGroups.BASIC);
                job.setState(JobStates.UNSTART);
            }

            //3.转换成Map
            Map<String,JobMetadata> map = new HashMap<>();
            for(JobMetadata job : defaultJobList){
                map.put(job.getId(),job);
            }

            //4.获取redis中已有的基础job
            this.updatePlanBasicJobs(jobCategoryKey, map);

            logger.debug("JobMetadataService.addDefaultBasicJobs 为类别:{} refresh basic jobs.", jobCategoryKey);

        } catch (Exception ex) {
            logger.error("JobMetadataService.addDefaultBasicJobs 为类别:{} refresh basic jobs err:{}", jobCategoryKey, ex.getMessage());
        }

    }

    private void updatePlanBasicJobs(String jobCategoryKey, Map<String,JobMetadata> basicJobs) {
//        JobsManifest manifest = null;
//        try {
//            RedisUtils.lock(RedisLocks.LOCK_STR_JOBMETADATA);
//            String json = RedisUtils.hget(RedisKeys.KEY_STR_JOBMETADATA, String.valueOf(planId));
//            if (json != null)
//                manifest = JsonUtils.toObject(json, JobsManifest.class);
//
//            if (manifest != null) {
//                manifest.setBasicJobs(mergeJobs(manifest.getBasicJobs(), basicJobs));
//            } else {
//                manifest = IntellissJobVars.createJobsManifest();
//                manifest.setBasicJobs(basicJobs);
//            }
//
//            RedisUtils.hset(RedisKeys.KEY_STR_JOBMETADATA, String.valueOf(planId), manifest);
//
//        } finally {
//            RedisUtils.unlock(RedisLocks.LOCK_STR_JOBMETADATA);
//        }
    }
}
