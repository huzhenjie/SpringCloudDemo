package com.scrat.background.module.async;

import com.scrat.background.model.Res;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

@RestController
public class AsyncController {
    private static final Logger log = LoggerFactory.getLogger(AsyncController.class);
    private final AsyncService asyncService;
    @Autowired
    public AsyncController(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    @GetMapping("/async")
    public Res<Object> asyncReq() {
        this.asyncService.asyncFutureTask();
        return Res.success();
    }

    @GetMapping("/async/uncaught_exception")
    public Res<Object> testGlobalException() {
        this.asyncService.testGlobalException();
        return Res.success();
    }

    @GetMapping("/sync")
    public Res<Object> asyncFutureTask() {
        Future<Boolean> result = this.asyncService.asyncFutureTask();
        try {
            Boolean data = result.get();
            log.info("result = {}", data);
        } catch (Exception e) {
            log.error("Something wrong", e);
        }

        return Res.success();
    }

    @GetMapping("/sync/multi")
    public Res<Object> multiAsyncTask() {
        Future<Boolean> task1 = this.asyncService.asyncFutureTask();
        Future<Boolean> task2 = this.asyncService.asyncFutureTask();
        try {
            Boolean data1 = task1.get();
            Boolean data2 = task2.get();
            Boolean data3 = task2.get();
            log.info("result1 = {}", data1);
            log.info("result2 = {}", data2);
            log.info("result3 = {}", data3); // Get result only
        } catch (Exception e) {
            log.error("Something wrong", e);
        }
        return Res.success();
    }

    @GetMapping("/sync/listenable")
    public Res<Object> asyncListenableTask() {
        ListenableFuture<Boolean> result = this.asyncService.asyncListenableTask();
        result.addCallback(
                result1 -> log.info("callback1: result = {}", result1),
                ex -> log.error("callback1: Something wrong", ex)
        );
        result.addCallback(
                result1 -> log.info("callback2: result = {}", result1),
                ex -> log.error("callback2: Something wrong", ex)
        );
        try {
            Boolean data = result.get();
            log.info("result = {}", data);
        } catch (Exception e) {
            log.error("Something wrong", e);
        }
        return Res.success();
    }
}
