package com.scrat.background.module.test;

import org.springframework.stereotype.Service;

@Service
public class TestService {

    public void runtimeException() {
        throw new RuntimeException("Something wrong");
    }
}
