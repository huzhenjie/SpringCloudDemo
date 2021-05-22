package com.scrat.background.module.env;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Repository;

import java.net.Inet4Address;
import java.net.InetAddress;

@Repository
public class EnvConfig {
    @Value("${spring.profiles.active}")
    private String active;
    @Value("${server.port}")
    private String port;

    private final BuildProperties buildProperties;

    public EnvConfig(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    public String getActive() {
        return active;
    }

    public String getPort() {
        return port;
    }

    public boolean isDev() {
        return "dev".equals(getActive());
    }

    public boolean isTest() {
        return "test".equals(getActive());
    }

    public boolean isProd() {
        return "prod".equals(getActive());
    }

    public boolean isUnitTest() {
        return "-1".equals(getPort());
    }

    public String getBuildDescription() {
        return buildProperties.getGroup() + "."
                + buildProperties.getArtifact() + ":"
                + buildProperties.getName() + ":"
                + buildProperties.getVersion()
                + " Build at " + buildProperties.getTime().toEpochMilli();
    }

    public String getServerIp() {
        try {
            InetAddress inetAddress = Inet4Address.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (Exception e) {
            return "0.0.0.0";
        }
    }

}
