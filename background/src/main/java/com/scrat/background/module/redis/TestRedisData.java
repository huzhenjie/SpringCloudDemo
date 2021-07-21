package com.scrat.background.module.redis;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TestRedisData {
    private long id;
    private String name;
    private Integer num;
    private String nullVal;
    private String moreProperties;

    private static final String PRIMARY_STATIC_PROPERTY = "primary_static_property";
    public static final String PUBLIC_STATIC_PROPERTY = "public_static_property";

    public String publicMethod() {
        return "publicMethod";
    }

    @JsonIgnore
    public String publicIgnoreMethod() {
        return "publicIgnoreMethod";
    }

    public long getId() {
        return id;
    }

    public TestRedisData setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TestRedisData setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getNum() {
        return num;
    }

    public TestRedisData setNum(Integer num) {
        this.num = num;
        return this;
    }

    public String getNullVal() {
        return nullVal;
    }

    public TestRedisData setNullVal(String nullVal) {
        this.nullVal = nullVal;
        return this;
    }

    public String getMoreProperties() {
        return moreProperties;
    }

    public TestRedisData setMoreProperties(String moreProperties) {
        this.moreProperties = moreProperties;
        return this;
    }

    @Override
    public String toString() {
        return "TestRedisData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", num=" + num +
                ", nullVal='" + nullVal + '\'' +
                ", moreProperties='" + moreProperties + '\'' +
                '}';
    }
}
