package com.scrat.background.module.date;

import com.scrat.background.model.Res;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@RestController
public class DateController {

    @GetMapping("/date")
    public Res<DateModel> getFormatDate() {
        Date now = new Date();
        ZoneOffset zone = ZoneOffset.ofHours(8);
        DateModel model = new DateModel()
                .setDate(now)
                .setDatetime(now)
                .setLocalDate(LocalDate.now(zone))
                .setLocalDateTime(LocalDateTime.now(zone));
        return Res.success(model);
    }
}
