package com.scrat.background.module.i18n;

import com.scrat.background.model.Res;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class I18nController {

    private final MessageSource messageSource;

    public I18nController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/i18n")
    public Res<String> getMsg() {
        Locale locale = LocaleContextHolder.getLocale();
        String content = messageSource.getMessage("msg.language", null, locale);
        String msg = messageSource.getMessage("msg.only.in.en", null, locale);
        return Res.success(content).setMessage(msg);
    }
}
