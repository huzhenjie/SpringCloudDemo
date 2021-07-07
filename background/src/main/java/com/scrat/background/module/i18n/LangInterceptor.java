package com.scrat.background.module.i18n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Locale;

public class LangInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(LangInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String lang = request.getHeader("lang");
        if (!StringUtils.hasLength(lang)) {
            lang = request.getParameter("lang");
        }
        if (StringUtils.hasLength(lang)) {
            try {
                LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
                if (localeResolver != null) {
                    localeResolver.setLocale(request, response, LocaleUtil.toLocale(lang));
                }
            } catch (Exception e) {
                log.error("Set locale fail, lang={}", lang, e);
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(new Locale("zh", "CN")); // zh_CN
        System.out.println(new Locale("zh", "cn")); // zh_CN
        System.out.println(Arrays.toString(Locale.getISOLanguages()));
        System.out.println(Arrays.toString(Locale.getISOCountries()));
    }
}
