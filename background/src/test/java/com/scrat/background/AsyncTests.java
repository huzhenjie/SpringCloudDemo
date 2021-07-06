package com.scrat.background;

import com.scrat.background.module.async.AsyncService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SpringBootTest
public class AsyncTests {
    @Autowired
    private AsyncService asyncService;
    @Test
    void testAsyncFutureTask() {
        Future<Boolean> res = asyncService.asyncFutureTask();
        try {
            Boolean success = res.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();

        }
    }
}
