package com.scrat.background;

import com.scrat.background.module.env.EnvConfig;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackgroundApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(BackgroundApplicationTests.class.getName());

    @Autowired
    private EnvConfig envConfig;

    @Test
    void contextLoads() {
        log.info(envConfig.getBuildDescription());
    }

}
