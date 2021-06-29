package com.scrat.background.module.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DateUtil {

    private static final Map<String, ThreadLocal<SimpleDateFormat>> dateLocalMap
            = new ConcurrentHashMap<String, ThreadLocal<SimpleDateFormat>>() {{
        put("yyyy-MM-dd HH:mm:ss", ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
        put("yyyy-MM-dd", ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd")));
    }};

    public static String formatDate(Date date, String fmt) {
        ThreadLocal<SimpleDateFormat> threadLocal = dateLocalMap.get(fmt);
        if (threadLocal == null) {
            throw new IllegalArgumentException("The given pattern is invalid " + fmt);
        }
        return threadLocal.get().format(date);
    }

    public static Date parseDate(String dateStr, String fmt) throws ParseException {
        ThreadLocal<SimpleDateFormat> threadLocal = dateLocalMap.get(fmt);
        if (threadLocal == null) {
            throw new IllegalArgumentException("The given pattern is invalid " + fmt);
        }
        return threadLocal.get().parse(dateStr);
    }

    public static long firstTsOfMonth(long ts) {
        ZoneOffset zone = ZoneOffset.ofHours(8);
        LocalDate localDate = Instant.ofEpochMilli(ts).atZone(zone).toLocalDate();
        return localDate.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(zone).toInstant().toEpochMilli();
    }

    public static long lastTsOfMonth(long ts) {
        ZoneOffset zone = ZoneOffset.ofHours(8);
        LocalDate localDate = Instant.ofEpochMilli(ts).atZone(zone).toLocalDate();
        return localDate.with(TemporalAdjusters.lastDayOfMonth())
                .plusDays(1)
                .atStartOfDay(zone)
                .toInstant()
                .toEpochMilli() - 1;
    }

    public static long nextMonthTs(long ts) {
        ZoneOffset zone = ZoneOffset.ofHours(8);
        LocalDateTime localDate = Instant.ofEpochMilli(ts).atZone(zone).toLocalDateTime();
        return localDate.plusMonths(1).atZone(zone).toInstant().toEpochMilli();
    }

    public static long lastMonthTs(long ts) {
        ZoneOffset zone = ZoneOffset.ofHours(8);
        LocalDateTime localDate = Instant.ofEpochMilli(ts).atZone(zone).toLocalDateTime();
        return localDate.minusMonths(1).atZone(zone).toInstant().toEpochMilli();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(LocalDateTime.now().toString()); // 2021-06-29T18:37:20.432

        long now = System.currentTimeMillis();
        System.out.println(firstTsOfMonth(now));
        System.out.println(lastTsOfMonth(now));

        System.out.println(nextMonthTs(1611883112000L)); // 2021-01-29 09:18:32 -> 2021-02-28 09:18:32
        System.out.println(lastMonthTs(1616980712000L)); // 2021-03-29 09:18:32 -> 2021-02-28 09:18:32

        System.out.println(formatDate(new Date(), "yyyy-MM-dd"));
        System.out.println(formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        System.out.println(parseDate("2021-06-29 16:02:09", "yyyy-MM-dd HH:mm:ss"));
    }
}
