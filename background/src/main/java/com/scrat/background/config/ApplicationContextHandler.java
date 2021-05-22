package com.scrat.background.config;

import com.scrat.background.module.env.EnvConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextHandler implements ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(ApplicationContextHandler.class.getName());
    private final EnvConfig envConfig;

    @Autowired
    public ApplicationContextHandler(EnvConfig envConfig) {
        this.envConfig = envConfig;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("Server running at:\thttp://{}:{}", envConfig.getServerIp(), envConfig.getPort());
    }
}
