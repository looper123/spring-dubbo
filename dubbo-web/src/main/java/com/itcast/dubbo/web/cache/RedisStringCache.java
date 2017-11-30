package com.itcast.dubbo.web.cache;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by zebon lu on 2017/6/13.
 */
public class RedisStringCache {

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    @Resource(name = "keySerializer")
    private RedisSerializer keySerializer;

    @Resource(name = "valueSerializer")
    private RedisSerializer valueSerializer;
    /**
     * set key value
     * cover key if exists
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * set key only if not exists
     *
     * @param key
     * @param value
     */
    public void setIfAbsent(String key, Object value) {
        redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * set key with expire time (seconds)
     *
     * @param key
     * @param value
     */
    public void setWithExpire(String key, Object value, long expire) {
        redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);

    }

    /**
     * multi set with pipelined
     *
     * @param tuple
     * @return
     */
    public Object mSetWithPipelined(final Map<String, ? extends Object> tuple) {
        Object result = redisTemplate.executePipelined(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection connection) throws DataAccessException {
                for (Map.Entry<String, ? extends Object> entry : tuple.entrySet()) {
                    byte[] key = keySerializer.serialize(entry.getKey());
                    byte[] value = valueSerializer.serialize(entry.getValue());
                    connection.set(key, value);
                }
                return null;
            }
        });
        return result;
    }


    /**
     * multi set with pipelined and expire
     *
     * @param map
     * @param expire
     * @return
     */
    public Object msetWithPipelinedAndExpire(final Map<String, Object> map, final long expire) {
        Object result = redisTemplate.executePipelined(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection connection) throws DataAccessException {
                for (Map.Entry<String, Object> entrySet : map.entrySet())
                    connection.setEx(keySerializer.serialize(entrySet.getKey()), expire, valueSerializer.serialize(entrySet.getValue()));
                return null;
            }
        });
        return result;
    }

    /**
     * delete one key
     *
     * @param key
     * @return
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * multi delete
     *
     * @param pattern
     * @return
     */
    public void mDelete(String pattern) {
        Set keys = redisTemplate.keys(pattern + "*");
        redisTemplate.delete(keys);
    }

    /**
     * get value by key
     *
     * @param key
     * @return
     */
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }


    /**
     * get expire time  default seconds
     *
     * @param key
     * @return
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }


    /**
     * flush all database
     */
    public void flushAll() {
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushAll();
                return null;
            }
        });
    }

    /**
     * flush this database
     */
    public void flushDB() {
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return null;
            }
        });
    }
}
