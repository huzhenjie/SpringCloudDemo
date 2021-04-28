package com.scrat.background.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextProvider implements ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(ApplicationContextProvider.class.getName());
    private final EnvConfig envConfig;

    @Autowired
    public ApplicationContextProvider(EnvConfig envConfig) {
        this.envConfig = envConfig;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("服务启动成功，请访问 http://{}:{}", envConfig.getServerIp(), envConfig.getPort());
    }
}
