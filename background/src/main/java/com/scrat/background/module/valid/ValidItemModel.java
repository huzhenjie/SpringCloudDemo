package com.scrat.background.module.valid;

import javax.validation.constraints.NotBlank;

public class ValidItemModel {
    private int id;
    @NotBlank(message = "name require")
    private String name;

    public int getId() {
        return id;
    }

    public ValidItemModel setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ValidItemModel setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "ValidItemModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
