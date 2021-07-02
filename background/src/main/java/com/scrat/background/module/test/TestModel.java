package com.scrat.background.module.test;

public class TestModel {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public TestModel setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TestModel setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "TestModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
