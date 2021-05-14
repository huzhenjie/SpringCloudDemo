package com.scrat.background.module.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Future;

@Service
public class AsyncService {
    private static final Logger log = LoggerFactory.getLogger(AsyncService.class);

    @Async
    public Future<Boolean> asyncFutureTask() {
        log.info("asyncFutureTask begin");

        try {
            log.info("Current thread: {}", Thread.currentThread().getName());
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            log.error("Sleep err", e);
            return AsyncResult.forExecutionException(e);
        }

        log.info("asyncFutureTask end");
        return AsyncResult.forValue(true);
    }

    @Async
    public ListenableFuture<Boolean> asyncListenableTask() {
        log.info("asyncListenableTask begin");

        try {
            log.info("Current thread: {}", Thread.currentThread().getName());
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            log.error("Sleep err", e);
            return AsyncResult.forExecutionException(e);
        }

        log.info("asyncListenableTask end");
        return AsyncResult.forValue(true);
    }

    @Async
    public void testGlobalException() {
        throw new RuntimeException("test global exception");
    }
}
