package lx.job.base.config;

import lx.job.base.utils.PathUtils;
import lx.job.base.utils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Paths;

/**
 * Redis配置
 * Created by lxliu@iflytek.com on 2016/8/25.
 */
public class RedisConfigurator {
    private static final Logger logger = LoggerFactory.getLogger(RedisConfigurator.class);

    private static final String DEFAULT_PROPERTIES_NAME ="redis.properties";

    //redis
    private static final String KEY_REDIS_HOST="redis.host";
    private static final String KEY_REDIS_PORT="redis.port";
    private static final String KEY_REDIS_AUTH="redis.auth";
    private static final String KEY_REDIS_MAX_ACTIVE="redis.max_active";
    private static final String KEY_REDIS_MAX_IDLE="redis.max_idle";
    private static final String KEY_REDIS_MAX_WAIT="redis.max_wait";
    private static final String KEY_REDIS_TIMEOUT="redis.timeout";
    private static final String KEY_REDIS_TEST_ON_BORROW="redis.test_on_borrow";

    private static String redisHost= "127.0.0.1";
    private static int redisPort = 6379;
    private static String redisAuth = null;
    private static int redisMaxActive=8;
    private static int redisMaxIdle = 8;
    private static int redisMaxWait = 30000;//millseconds
    private static int redisTimeout = 60000;//millseconds
    private static boolean redisTestOnBorrow = true;

    static {
        try {
            String appRoot = PathUtils.getProjectPath(RedisConfigurator.class);
            String propertiesFile = Paths.get(appRoot,DEFAULT_PROPERTIES_NAME).toString();
            if(new File(propertiesFile).exists()) {
                PropertyUtils props = PropertyUtils.createInstance(propertiesFile);
                redisHost = props.getProperty(KEY_REDIS_HOST);
                redisPort = props.getPropertyInt(KEY_REDIS_PORT);
                redisAuth = props.getProperty(KEY_REDIS_AUTH);
                redisMaxActive = props.getPropertyInt(KEY_REDIS_MAX_ACTIVE);
                redisMaxIdle = props.getPropertyInt(KEY_REDIS_MAX_IDLE);
                redisMaxWait = props.getPropertyInt(KEY_REDIS_MAX_WAIT);
                redisTimeout = props.getPropertyInt(KEY_REDIS_TIMEOUT);
                redisTestOnBorrow = props.getPropertyBool(KEY_REDIS_TEST_ON_BORROW);
                props.destroyInstance();
            }
        }catch (Exception e){
            logger.error("RedisConfigurator static error .{}",e);
        }
    }

    /**
     * @param redisPropertiesFilePath  : Absolute Path
     */
    public static void configure(String redisPropertiesFilePath){

        try {
            PropertyUtils props = PropertyUtils.createInstance(redisPropertiesFilePath);
            redisHost = props.getProperty(KEY_REDIS_HOST);
            redisPort = props.getPropertyInt(KEY_REDIS_PORT);
            redisAuth = props.getProperty(KEY_REDIS_AUTH);
            redisMaxActive = props.getPropertyInt(KEY_REDIS_MAX_ACTIVE);
            redisMaxIdle = props.getPropertyInt(KEY_REDIS_MAX_IDLE);
            redisMaxWait = props.getPropertyInt(KEY_REDIS_MAX_WAIT);
            redisTimeout = props.getPropertyInt(KEY_REDIS_TIMEOUT);
            redisTestOnBorrow = props.getPropertyBool(KEY_REDIS_TEST_ON_BORROW);
            props.destroyInstance();

            logger.info("read redis properties from :{}", redisPropertiesFilePath);
        }catch (Exception e){
            logger.info("read redis properties from :{},error :{}", redisPropertiesFilePath,e.getMessage());
        }
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
