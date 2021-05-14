package com.scrat.background.module.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Only for @Async void methods.
 */
@Component
public class GlobalAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalAsyncExceptionHandler.class);

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.error("[ GlobalAsyncException ][ {} ] {}", method, params, ex);
    }
}
