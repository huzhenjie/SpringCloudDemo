package com.scrat.background;

import com.scrat.background.module.redis.BaseRedis;
import com.scrat.background.module.redis.TestRedisData;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisTests {
    private static final Logger log = LoggerFactory.getLogger(MySQLTests.class.getName());
    @Autowired
    private BaseRedis baseRedis;

    @Test
    void testSetData() {
        String key = "TestKey";
        baseRedis.setValue(key, new TestRedisData().setId(12345L).setName("Hello world").setMoreProperties("More"));
        log.info("{}", baseRedis.getValue(key, TestRedisData.class));
//        baseRedis.setValue(key, "str");
//        log.info("{}", baseRedis.getValue(key, String.class));
//        baseRedis.setValue(key, 1);
//        log.info("{}", baseRedis.getValue(key, Integer.class));
//        baseRedis.setValue(key, 0.123456d);
//        log.info("{}", baseRedis.getValue(key, Double.class));
//        baseRedis.setValue(key, 0.56f);
//        log.info("{}", baseRedis.getValue(key, Float.class));
//        baseRedis.setValue(key, 1234567890L);
//        log.info("{}", baseRedis.getValue(key, Long.class));
//        log.info("{}", baseRedis.getValue("key not found", Long.class));
    }

    @Test
    void testCount() {
        String key = "TestCountKey";
        log.info("{}", baseRedis.getCurrCount(key)); // 0
        log.info("{}", baseRedis.increment(key, 10L * 60_000L, 1L)); // 1
        log.info("{}", baseRedis.getCurrCount(key)); // 1
        log.info("{}", baseRedis.increment(key, 10L * 60_000L, 3L)); // 4
        log.info("{}", baseRedis.getCurrCount(key)); // 4
        log.info("{}", baseRedis.decrement(key, 10L * 60_000L, 1L)); // 3
        log.info("{}", baseRedis.getCurrCount(key)); // 3
        log.info("{}", baseRedis.decrement(key, 10L * 60_000L, 0L)); // 3
        log.info("{}", baseRedis.getCurrCount(key)); // 3
        log.info("{}", baseRedis.decrement(key, 10L * 60_000L, 20L)); // -17
        log.info("{}", baseRedis.getCurrCount(key)); // -17
    }

    @Test
    void testLock() {
        String key = "TestLockKey";
        boolean lockRes = baseRedis.lock(key);
        log.info("{}", lockRes); // true
        lockRes = baseRedis.lock(key);
        log.info("{}", lockRes); // false
        baseRedis.unlock(key);
        lockRes = baseRedis.lock(key);
        log.info("{}", lockRes); // true
    }
}
