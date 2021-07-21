package com.scrat.background.module.redis;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class BaseRedis {
    private static final Logger log = LoggerFactory.getLogger(BaseRedis.class.getName());
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public BaseRedis(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void setValue(String key, Object obj) {
        setValue(key, obj, 15L * 60_000L); // default 15 minute
    }

    public void setValue(String key, Object obj, long milliseconds) {
        redisTemplate.opsForValue().set(key, obj, milliseconds, TimeUnit.MILLISECONDS);
    }

    public <T> T getValue(String key, Class<T> tClass) {
        return getValue(key, tClass, null);
    }

    public <T> T getValue(String key, Class<T> tClass, T defaultVal) {
        try {
            Object obj = redisTemplate.opsForValue().get(key);
            return objectMapper.convertValue(obj, tClass);
        } catch (Exception e) {
            log.error("convert redis val fail. key={} class={}", key, tClass, e);
            return defaultVal;
        }
    }

    /**
     * <pre>
     *     JavaType javaType = mapper.getTypeFactory().constructParametricType(Res.class, Model.class);
     * </pre>
     * @param key Redis key
     * @param javaType Return value type
     * @param defaultVal Default value if not found in redis
     * @param <T> Template
     * @return Redis value
     */
    public <T> T getValue(String key, JavaType javaType, T defaultVal) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object obj = redisTemplate.opsForValue().get(key);
            return mapper.convertValue(obj, javaType);
        } catch (Exception e) {
            log.error("convert redis val fail. key={} javaType={}", key, javaType, e);
            return defaultVal;
        }
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public boolean has(String key) {
        Boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey == null) {
            return false;
        }
        return hasKey;
    }

    // lock

    private String getLockKey(String key) {
        return "LOCK:" + key;
    }

    public boolean lock(String key) {
        return lock(key, 15L * 60_000L); // default 15 minute
    }

    public boolean lock(String key, long milliseconds) {
        String redisKey = getLockKey(key);
        String expireTs = String.valueOf(System.currentTimeMillis() + milliseconds);
        Boolean setSuccess = redisTemplate.opsForValue().setIfAbsent(
                redisKey, expireTs, milliseconds, TimeUnit.MILLISECONDS);
        return setSuccess != null && setSuccess;
    }

    public void unlock(String key) {
        String redisKey = getLockKey(key);
        redisTemplate.delete(redisKey);
    }

    // count

    private String getCountKey(String key) {
        return "COUNT:" + key;
    }

    public Long increment(String key, long milliseconds, long delta) {
        String countKey = getCountKey(key);
        Long currNum = redisTemplate.opsForValue().increment(countKey, delta);
        redisTemplate.expire(countKey, milliseconds, TimeUnit.MILLISECONDS);
        return currNum;
    }

    public Long decrement(String key, long milliseconds, long delta) {
        String countKey = getCountKey(key);
        Long currNum = redisTemplate.opsForValue().decrement(countKey, delta);
        redisTemplate.expire(countKey, milliseconds, TimeUnit.MILLISECONDS);
        return currNum;
    }

    public Long getCurrCount(String key) {
        String countKey = getCountKey(key);
        return redisTemplate.opsForValue().increment(countKey, 0L);
    }
}
