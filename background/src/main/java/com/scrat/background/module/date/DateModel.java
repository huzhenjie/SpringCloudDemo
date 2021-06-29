package com.scrat.background.module.date;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class DateModel {
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate localDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date datetime;

    public LocalDate getLocalDate() {
        return localDate;
    }

    public DateModel setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
        return this;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public DateModel setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public DateModel setDate(Date date) {
        this.date = date;
        return this;
    }

    public Date getDatetime() {
        return datetime;
    }

    public DateModel setDatetime(Date datetime) {
        this.datetime = datetime;
        return this;
    }

    @Override
    public String toString() {
        return "DateModel{" +
                "localDate=" + localDate +
                ", localDateTime=" + localDateTime +
                ", date=" + date +
                ", datetime=" + datetime +
                '}';
    }
}
