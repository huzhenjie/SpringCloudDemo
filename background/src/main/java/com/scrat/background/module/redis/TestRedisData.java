package com.scrat.background.module.redis;

public class TestRedisData {
    private long id;
    private String name;
    private Integer num;

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

    @Override
    public String toString() {
        return "TestRedisData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", num=" + num +
                '}';
    }
}
