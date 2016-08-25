package lx.job.base.entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 所有作业的清单信息
 * Created by lxliu@iflytek.com on 2016/8/25.
 */
public class JobsManifest {

    //基础作业，包含缓存、统计等
    private Map<String,JobMetadata> basicJobs = new HashMap<>();

    //结果汇总作业
    private Map<String, JobMetadata> resultJobs = new HashMap<>();

    public Map<String, JobMetadata> getBasicJobs() {
        return basicJobs;
    }

    public void setBasicJobs(Map<String, JobMetadata> basicJobs) {
        this.basicJobs = basicJobs;
    }

    public Map<String, JobMetadata> getResultJobs() {
        return resultJobs;
    }

    public void setResultJobs(Map<String, JobMetadata> resultJobs) {
        this.resultJobs = resultJobs;
    }

    public Map<String,JobMetadata> getAll(){
        Map<String,JobMetadata> results = new HashMap<>();
        if(basicJobs!=null && basicJobs.size()>0){
            results.putAll(basicJobs);
        }

        if (resultJobs != null && resultJobs.size() > 0) {
            results.putAll(resultJobs);
        }

        return results;
    }

    public List<JobMetadata> toList(){
        List<JobMetadata> results = new LinkedList<>();
        if(basicJobs!=null && basicJobs.size()>0){
            results.addAll(basicJobs.values());
        }

        if (resultJobs != null && resultJobs.size() > 0) {
            results.addAll(resultJobs.values());
        }
        return results;
    }
}
