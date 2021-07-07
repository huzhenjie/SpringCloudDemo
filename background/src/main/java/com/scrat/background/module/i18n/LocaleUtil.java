package com.scrat.background.module.i18n;

import java.util.Locale;

public class LocaleUtil {
    public static Locale toLocale(String lang) {
        return toLocale(lang, "_");
    }

    public static Locale toLocale(String lang, String splitRegex) {
        String[] langArr = lang.split(splitRegex);
        if (langArr.length > 1) {
            return new Locale(langArr[0], langArr[1]);
        }
        return new Locale(langArr[0]);
    }
}
