package com.scrat.background.model;

import java.io.Serializable;

public class QueueData<T> implements Serializable {
    private int type;
    private T data;

    public int getType() {
        return type;
    }

    public QueueData<T> setType(int type) {
        this.type = type;
        return this;
    }

    public T getData() {
        return data;
    }

    public QueueData<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "QueueData{" +
                "type=" + type +
                ", data=" + data +
                '}';
    }
}
