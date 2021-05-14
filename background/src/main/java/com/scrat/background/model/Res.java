package com.scrat.background.model;

public class Res<T> {
    private int status;
    private String message;
    private T data;

    public static <T> Res<T> success() {
        return new Res<T>()
                .setStatus(200);
    }

    public static <T> Res<T> error(int code, String msg) {
        return new Res<T>()
                .setStatus(code)
                .setMessage(msg);
    }

    public int getStatus() {
        return status;
    }

    public Res<T> setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Res<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Res<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "Res{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
