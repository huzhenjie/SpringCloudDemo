package com.scrat.background.module.encrypt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("http.encrypt")
@Configuration
public class EncryptProperties {
    private final static String DEFAULT_KEY = "wfxAbPXiGMHRCdE7";
    private String key = DEFAULT_KEY;
    private String iv = DEFAULT_KEY;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIv() {
        return iv;
    }

    public EncryptProperties setIv(String iv) {
        this.iv = iv;
        return this;
    }
}
