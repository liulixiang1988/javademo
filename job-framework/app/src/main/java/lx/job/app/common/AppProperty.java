package lx.job.app.common;

import lx.job.base.utils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lxliu@iflytek.com on 2016/8/25.
 */
public class AppProperty {
    private static final Logger logger = LoggerFactory.getLogger(AppProperty.class);

    //server
    private static final String KEY_SERVER_PORT = "server.port";
    private static final String KEY_STATICFILE_EXTERNAL="staticFiles.useExternal";
    private static final String KEY_STATICFILE_DIR="staticFiles.location";
    private static final String KEY_STATICFILE_EXPIRETIME="staticFiles.expireTime";
    private static final String KEY_SESSION_EXPIRETIME="session.expireTime";

    //redis
    private static final String KEY_REDIS_HOST="redis.host";
    private static final String KEY_REDIS_PORT="redis.port";
    private static final String KEY_REDIS_AUTH="redis.auth";
    private static final String KEY_REDIS_MAX_ACTIVE="redis.max_active";
    private static final String KEY_REDIS_MAX_IDLE="redis.max_idle";
    private static final String KEY_REDIS_MAX_WAIT="redis.max_wait";
    private static final String KEY_REDIS_TIMEOUT="redis.timeout";
    private static final String KEY_REDIS_TEST_ON_BORROW="redis.test_on_borrow";

    private static final String KEY_JOB_MANAGER_INTERVAL="jobmanager.trigger";

    //database
    //private static final String KEY_REDIS_HOST="redis.host";


    private static int serverPort = 4567;
    private static boolean staticFilesUseExternal=false;
    private static String staticFilesLocation = null;
    private static long staticFilesExpireTime = 60;//unit:seconds
    private static int sessionExpireTime = 180;

    private static String redisHost= "127.0.0.1";
    private static int redisPort = 6379;
    private static String redisAuth = null;
    private static int redisMaxActive=8;
    private static int redisMaxIdle = 8;
    private static int redisMaxWait = 30000;//millseconds
    private static int redisTimeout = 60000;//millseconds
    private static boolean redisTestOnBorrow = true;

    private static String jobManagerTrigger ="cron:0/10 * * * * ?"; //每10s

    static {
        try {
            String propertiesFile = AppPath.getAppPropFilePath();
            PropertyUtils props = PropertyUtils.createInstance(propertiesFile);

            serverPort = props.getPropertyInt(KEY_SERVER_PORT);
            staticFilesUseExternal = props.getPropertyBool(KEY_STATICFILE_EXTERNAL);
            staticFilesLocation = props.getProperty(KEY_STATICFILE_DIR);
            staticFilesExpireTime = props.getPropertyLong(KEY_STATICFILE_EXPIRETIME);
            sessionExpireTime = props.getPropertyInt(KEY_SESSION_EXPIRETIME);

            redisHost = props.getProperty(KEY_REDIS_HOST);
            redisPort = props.getPropertyInt(KEY_REDIS_PORT);
            redisAuth = props.getProperty(KEY_REDIS_AUTH);
            redisMaxActive = props.getPropertyInt(KEY_REDIS_MAX_ACTIVE);
            redisMaxIdle=props.getPropertyInt(KEY_REDIS_MAX_IDLE);
            redisMaxWait = props.getPropertyInt(KEY_REDIS_MAX_WAIT);
            redisTimeout = props.getPropertyInt(KEY_REDIS_TIMEOUT);
            redisTestOnBorrow = props.getPropertyBool(KEY_REDIS_TEST_ON_BORROW);

            jobManagerTrigger = props.getProperty(KEY_JOB_MANAGER_INTERVAL,jobManagerTrigger);


        }catch (Exception e){
            logger.error("AppProperty static error .{}",e);
        }
    }


    public static String getJobManagerTrigger() {
        return jobManagerTrigger;
    }

    public static void setJobManagerTrigger(String jobManagerTrigger) {
        AppProperty.jobManagerTrigger = jobManagerTrigger;
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static boolean isStaticFilesUseExternal() {
        return staticFilesUseExternal;
    }

    public static String getStaticFilesLocation() {
        return staticFilesLocation;
    }

    public static long getStaticFilesExpireTime() {
        return staticFilesExpireTime;
    }

    public static int getSessionExpireTime() {
        return sessionExpireTime;
    }

    public static String getRedisHost() {
        return redisHost;
    }

    public static int getRedisPort() {
        return redisPort;
    }

    public static String getRedisAuth() {
        return redisAuth;
    }

    public static int getRedisMaxActive() {
        return redisMaxActive;
    }

    public static int getRedisMaxIdle() {
        return redisMaxIdle;
    }

    public static int getRedisMaxWait() {
        return redisMaxWait;
    }

    public static int getRedisTimeout() {
        return redisTimeout;
    }

    public static boolean isRedisTestOnBorrow() {
        return redisTestOnBorrow;
    }

}
