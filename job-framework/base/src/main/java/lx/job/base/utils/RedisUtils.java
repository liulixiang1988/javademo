package lx.job.base.utils;

import com.sun.istack.internal.NotNull;
import lx.job.base.config.RedisConfigurator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Redis辅助
 * Created by lxliu@iflytek.com on 2016/8/25.
 */
public class RedisUtils {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    private static JedisPool jedisPool = null;

    //region #params - initial
    private static final Random r = new Random();
    //加锁标志
    private static final String LOCKED = "TRUE";
    //默认超时时间（毫秒）
    private static final long DEFAULT_TIME_OUT = 3000;
    //锁的超时时间（秒），过期删除
    private static final int EXPIRE = 5 * 60;
    //endregion

    //region #operation - pool init
    private static void initialPool() {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(RedisConfigurator.getRedisMaxActive());
            config.setMaxIdle(RedisConfigurator.getRedisMaxIdle());
            config.setMaxWaitMillis(RedisConfigurator.getRedisMaxWait());
            config.setTestOnBorrow(RedisConfigurator.isRedisTestOnBorrow());
            jedisPool = new JedisPool(config, RedisConfigurator.getRedisHost().split(",")[0],
                    RedisConfigurator.getRedisPort(), RedisConfigurator.getRedisTimeout());
        } catch (Exception e) {
            logger.error("First create JedisPool error : " + e);
            try {
                //如果第一个IP异常，则访问第二个IP
                JedisPoolConfig config = new JedisPoolConfig();
                config.setMaxTotal(RedisConfigurator.getRedisMaxActive());
                config.setMaxIdle(RedisConfigurator.getRedisMaxIdle());
                config.setMaxWaitMillis(RedisConfigurator.getRedisMaxWait());
                config.setTestOnBorrow(RedisConfigurator.isRedisTestOnBorrow());
                jedisPool = new JedisPool(config, RedisConfigurator.getRedisHost().split(",")[1],
                        RedisConfigurator.getRedisPort(), RedisConfigurator.getRedisTimeout());
            } catch (Exception e2) {
                logger.error("Second create JedisPool error : " + e2);
            }
        }
    }

    private static synchronized void poolInit() {
        if (jedisPool == null) {
            initialPool();
        }
    }

    private synchronized static Jedis getJedis() {
        if (jedisPool == null) {
            poolInit();
        }
        Jedis jedis = null;
        try {
            if (jedisPool != null) {
                jedis = jedisPool.getResource();
            }
        } catch (Exception e) {
            logger.error("get jedis error : " + e);
            if (jedis != null)
                jedis.close();
        } finally {

        }

        //logger.info("RedisUtils -> open redis instance. :{}", jedis);
        return jedis;
    }

    private synchronized static void releaseJedis(Jedis jedis) {
        //logger.debug("RedisUtils -> close redis ----------------------------");
        if (jedis != null) {
            // logger.debug("RedisUtils -> close redis instance. :{}", jedis.toString());
            jedis.close();
        }
    }
    //endregion

    //region #operation - lock & unlock
    public synchronized static boolean lock(String key) {
        long nano = System.nanoTime();
        long timeout = DEFAULT_TIME_OUT;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            while ((System.nanoTime() - nano) < timeout) {
                if (jedis.setnx(key, LOCKED) == 1) {
                    jedis.expire(key, EXPIRE);
                    return true;
                }
                // 短暂休眠，nano避免出现活锁
                Thread.sleep(3, r.nextInt(500));
            }
        } catch (Exception e) {
            logger.warn("获取redis lock 失败 .key->{},error->{}", key, e.getMessage());
        }
        finally {
            releaseJedis(jedis);
        }
        return false;
    }

    public synchronized static void unlock(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.del(key);
        } finally {
            releaseJedis(jedis);
        }
    }
    //endregion

    //region #operation - String & object

    public static Set<String> getStringKeys() {
        Jedis jedis = null;
        Set<String> value = null;
        try {
            jedis = getJedis();
            value = jedis.keys("*");

        } catch (Exception e) {
            logger.error("getString error. {}", e);
        } finally {
            releaseJedis(jedis);
        }
        return value;
    }

    public static boolean setString(String key, String obj) {
        Jedis jedis = null;
        boolean result = true;
        try {
            if (obj == null || StringUtils.isEmpty(key)) {
                throw new Exception("args has null value.");
            }

            jedis = getJedis();
            jedis.set(key, obj);

        } catch (Exception e) {
            logger.error("setString error. {}", e);
            result = false;
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static boolean setStringEx(String key, String obj, int timeout) {
        Jedis jedis = null;
        boolean result = true;
        try {
            if (obj == null || StringUtils.isEmpty(key)) {
                throw new Exception("args has null value.");
            }

            jedis = getJedis();
            jedis.setex(key, timeout, obj);

        } catch (Exception e) {
            logger.error("setStringEx error. {}", e);
            result = false;
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static String getString(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = getJedis();
            value = jedis.get(key);

        } catch (Exception e) {
            logger.error("getString error. {}", e);
        } finally {
            releaseJedis(jedis);
        }
        return value;
    }

    public static <T> boolean setObject(String key, T obj) {
        Jedis jedis = null;
        boolean result = true;
        try {
            if (obj == null || StringUtils.isEmpty(key)) {
                throw new Exception("args has null value.");
            }

            String json = JsonUtils.toJson(obj);
            jedis = getJedis();
            jedis.set(key, json);

        } catch (Exception e) {
            logger.error("setString error. {}", e);
            result = false;
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static <T> boolean setObject(String key, T obj, Class<T> valueType) {
        Jedis jedis = null;
        boolean result = true;
        try {
            if (obj == null || StringUtils.isEmpty(key)) {
                throw new Exception("args has null value.");
            }

            String json = JsonUtils.toJson(obj, valueType);
            jedis = getJedis();
            jedis.set(key, json);

        } catch (Exception e) {
            logger.error("setString error. {}", e);
            result = false;
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static <T> boolean setObjectEx(String key, T obj, int timeout) {
        Jedis jedis = null;
        boolean result = true;
        try {
            if (obj == null || StringUtils.isEmpty(key)) {
                throw new Exception("args has null value.");
            }

            String json = JsonUtils.toJson(obj);
            jedis = getJedis();
            jedis.setex(key, timeout, json);

        } catch (Exception e) {
            logger.error("setStringEx error. {}", e);
            result = false;
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static <T> T getObject(String key, Class<T> valueType) {
        Jedis jedis = null;
        String result = null;
        T value = null;
        try {
            jedis = getJedis();
            result = jedis.get(key);
            value = JsonUtils.toObject(result, valueType);
        } catch (Exception e) {
            logger.error("getString error. {}", e);
        } finally {
            releaseJedis(jedis);
        }
        return value;
    }

    public static boolean expireKey(String key, int newSeconds) {
        Jedis jedis = null;
        boolean result = true;
        try {
            if (StringUtils.isEmpty(key)) {
                throw new Exception("args has null value.");
            }

            jedis = getJedis();
            jedis.expire(key, newSeconds);

        } catch (Exception e) {
            logger.error("setString error. {}", e);
            result = false;
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static boolean existsKey(String key) {
        Jedis jedis = null;
        boolean result = true;
        try {
            if (StringUtils.isEmpty(key)) {
                throw new Exception("args has null value.");
            }

            jedis = getJedis();
            result = jedis.exists(key);

        } catch (Exception e) {
            logger.error("existsKey error. {}", e);
            result = false;
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static boolean deleteKey(String key) {
        Jedis jedis = null;
        boolean result = true;
        try {
            if (StringUtils.isEmpty(key)) {
                throw new Exception("args has null value.");
            }

            jedis = getJedis();
            jedis.del(key);

        } catch (Exception e) {
            logger.error("deleteKey error. {}", e);
            result = false;
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }


    //endregion -  -

    //region #operation - hash

    public static <T> boolean hset(String hashKey, String filed, T obj) {
        Jedis jedis = null;
        boolean result = true;
        try {
            if (obj == null || filed.isEmpty() || hashKey.isEmpty()) {
                throw new Exception("args has null value.");
            }

            String json = JsonUtils.toJson(obj);
            jedis = getJedis();
            jedis.hset(hashKey, filed, json);

        } catch (Exception e) {
            logger.error("setHash error. {}", e.getMessage());
            result = false;
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static String hget(String hashKey, String filed) {
        Jedis jedis = null;
        String json = null;
        try {
            if (StringUtils.isEmpty(filed) || StringUtils.isEmpty(hashKey)) {
                throw new Exception("args has null value.");
            }
            jedis = getJedis();
            json = jedis.hget(hashKey, filed);
        } catch (Exception e) {
            logger.error("getHashValue error. {}", e);
        } finally {
            releaseJedis(jedis);
        }
        return json;
    }

    public static <T> T hget(String hashKey, String filed, Class<T> valueType) {
        Jedis jedis = null;
        T result = null;
        String json = null;
        try {
            if (StringUtils.isEmpty(filed) || StringUtils.isEmpty(hashKey)) {
                throw new Exception("args has null value.");
            }

            jedis = getJedis();
            json = jedis.hget(hashKey, filed);

            result = JsonUtils.toObject(json, valueType);

        } catch (Exception e) {
            logger.error("getHashValue error. {}", e);
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static <T> T hget(String hashKey, String filed, Type typeOfT) {
        Jedis jedis = null;
        T result = null;
        String json = null;
        try {
            if (StringUtils.isEmpty(filed) || StringUtils.isEmpty(hashKey)) {
                throw new Exception("args has null value.");
            }

            jedis = getJedis();
            json = jedis.hget(hashKey, filed);

            result = JsonUtils.toObject(json, typeOfT);

        } catch (Exception e) {
            logger.error("getHashValue error. {}", e);
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static boolean hdel(String hashKey, String filed) {
        Jedis jedis = null;
        boolean result = true;
        try {
            if (StringUtils.isEmpty(filed) || StringUtils.isEmpty(hashKey)) {
                throw new Exception("args has null value.");
            }

            jedis = getJedis();
            jedis.hdel(hashKey, filed);

        } catch (Exception e) {
            logger.error("hdel error. {}", e);
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static boolean hdels(String hashKey, String[] fileds) {
        Jedis jedis = null;
        boolean result = true;
        try {
            if (fileds==null || fileds.length==0 || StringUtils.isEmpty(hashKey)) {
                throw new Exception("args has null value.");
            }
            jedis = getJedis();
            jedis.hdel(hashKey, fileds);

        } catch (Exception e) {
            logger.error("hdels error. {}", e);
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static boolean hexists(String hashKey, String filed) {
        Jedis jedis = null;
        boolean result = true;
        try {
            if (StringUtils.isEmpty(filed) || StringUtils.isEmpty(hashKey)) {
                throw new Exception("args has null value.");
            }

            jedis = getJedis();
            result = jedis.hexists(hashKey, filed);

        } catch (Exception e) {
            logger.error("hexists error. {}", e);
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static Set<String> hkeys(String hashKey) {
        Jedis jedis = null;
        Set<String> result = null;
        try {
            if (StringUtils.isEmpty(hashKey)) {
                throw new Exception("args has null value.");
            }

            jedis = getJedis();
            result = jedis.hkeys(hashKey);

        } catch (Exception e) {
            logger.error("hkeys error. {}", e);
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static List<String> hvals(String hashKey) {
        Jedis jedis = null;
        List<String> result = null;
        try {
            if (StringUtils.isEmpty(hashKey)) {
                throw new Exception("args has null value.");
            }

            jedis = getJedis();
            result = jedis.hvals(hashKey);

        } catch (Exception e) {
            logger.error("hvals error. {}", e);
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static boolean hmset(String hashKey, Map<String, String> values) {
        Jedis jedis = null;
        boolean result = true;
        try {
            jedis = getJedis();
            jedis.hmset(hashKey, values);

        } catch (Exception e) {
            logger.error("hmset error. {}", e);
            result = false;
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static List<String> hmget(String hashKey, String... fields) {
        Jedis jedis = null;
        List<String> result = null;
        try {
            jedis = getJedis();
            result = jedis.hmget(hashKey, fields);

        } catch (Exception e) {
            logger.error("hmget error. {}", e);
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static Map<String, String> hgetAll(String hashKey) {
        Jedis jedis = null;
        Map<String, String> result = null;
        try {
            jedis = getJedis();
            result = jedis.hgetAll(hashKey);

        } catch (Exception e) {
            logger.error("hgetAll error. {}", e);
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static <T> boolean hmsetT(String hashKey, Map<String, T> values, Type type) {
        Jedis jedis = null;
        boolean result = true;
        Map<String, String> map = new LinkedHashMap<String, String>();
        try {

            String tJson;
            for (String key : values.keySet()) {
                tJson = JsonUtils.toJson(values.get(key), type);
                map.put(key, tJson);
            }

            jedis = getJedis();
            jedis.hmset(hashKey, map);
        } catch (Exception e) {
            logger.error("hmsetT error. {}", e);
            result = false;
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static <T> List<T> hmgetT(String hashKey, Type type, String... fields) {
        Jedis jedis = null;
        List<T> result = new LinkedList<T>();
        List<String> tObjs = new LinkedList<String>();
        try {
            jedis = getJedis();
            tObjs = jedis.hmget(hashKey, fields);

            for (String str : tObjs) {
                T obj = JsonUtils.toObject(str, type);
                result.add(obj);
            }

        } catch (Exception e) {
            logger.error("hmgetT error. {}", e);
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static <T> Map<String, T> hgetAllT(String hashKey, Type typeOfT) {
        Jedis jedis = null;
        Map<String, T> result = new LinkedHashMap<String, T>();
        Map<String, String> map = null;
        try {
            jedis = getJedis();
            map = jedis.hgetAll(hashKey);
            if (map != null && map.size() > 0) {
                for (String key : map.keySet()) {
                    T obj = JsonUtils.toObject(map.get(key), typeOfT);
                    result.put(key, obj);
                }
            }

        } catch (Exception e) {
            logger.error("hgetAllT error. {}", e);
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }
    //endregion

    //region "sorted set"
    public static long zcount(@NotNull final String sortedSetKey) {
        Jedis jedis = null;
        long count = -1;
        try {
            jedis = getJedis();
            count = jedis.zcount(sortedSetKey,Double.MIN_VALUE,Double.MAX_VALUE);

        } catch (Exception e) {
            logger.error("zcount error. {}", e);
        } finally {
            releaseJedis(jedis);
        }
        return count;
    }

    public static <T> boolean zadd(@NotNull final String hashKey, @NotNull final T... data) {
        Jedis jedis = null;
        boolean result = true;
        try {
            if (data == null || StringUtils.isEmpty(hashKey)) {
                throw new Exception("args has null value.");
            }

            Map<String, Double> json = Arrays.stream(data).map(JsonUtils::toJson).collect(Collectors.toMap(t -> t, t -> 0d));
            jedis = getJedis();
            jedis.zadd(hashKey, json);

        } catch (Exception e) {
            logger.error("zadd error. {}", e);
            result = false;
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static <T> boolean zadd(@NotNull final String hashKey, @NotNull final Set<T> datas) {
        Jedis jedis = null;
        boolean result = true;
        Map<String, Double> map = new HashMap<>();
        try {

            String tJson;
            for (T data : datas) {
                tJson = JsonUtils.toJson(data);
                map.put(tJson, 0d);
            }

            jedis = getJedis();
            jedis.zadd(hashKey, map);
        } catch (Exception e) {
            logger.error("zadd error. {}", e);
            result = false;
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static <T> Set<T> zrange(@NotNull final String hashKey, long start, long end, Type type) {
        Jedis jedis = null;
        Set<T> result = new LinkedHashSet<T>();
        Set<String> tObjs;
        try {
            jedis = getJedis();
            tObjs = jedis.zrange(hashKey, start, end);
            for (String str : tObjs) {
                T obj = JsonUtils.toObject(str, type);
                result.add(obj);
            }
        } catch (Exception e) {
            logger.error("zrange error. {}, {}", e.getMessage(), e.getStackTrace());
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static <T> Set<T> zrange(@NotNull final String hashKey, long count, Type type) {
        return zrange(hashKey, 0, count, type);
    }

    public static <T> boolean zrem(@NotNull final String hashKey, @NotNull Set<T> datas) {
        Jedis jedis = null;
        boolean result = true;
        try {
            String[] jsons = datas.stream().map(JsonUtils::toJson).toArray(String[]::new);
            jedis = getJedis();
            jedis.zrem(hashKey, jsons);
        } catch (Exception e) {
            logger.error("zrem error. {}", e);
            result = false;
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static <T> boolean zrem(@NotNull final String hashKey, @NotNull T data) {
        Jedis jedis = null;
        boolean result = true;
        try {
            jedis = getJedis();
            jedis.zrem(hashKey, JsonUtils.toJson(data));
        } catch (Exception e) {
            logger.error("zrem error. {}", e);
            result = false;
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }

    public static <T> boolean zremByRank(@NotNull final String hashKey,int start,int stop) {
        Jedis jedis = null;
        boolean result = true;
        try {
            jedis = getJedis();
            jedis.zremrangeByRank(hashKey,start,stop);
        } catch (Exception e) {
            logger.error("zremByRank error. {}", e);
            result = false;
        } finally {
            releaseJedis(jedis);
        }
        return result;
    }
    //endregion

}
