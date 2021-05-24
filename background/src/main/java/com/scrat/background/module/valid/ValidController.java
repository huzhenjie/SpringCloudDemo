package com.scrat.background.module.valid;

import com.scrat.background.model.Res;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ValidController {
    private static Logger log = LoggerFactory.getLogger(ValidController.class.getName());

    /**
     * curl -X POST -H 'Content-Type: application/json' -d '{"item_name": "This is ItemName"}' http://localhost:18001/valid/json
     */
    @PostMapping("/valid/json")
    public Res<Object> testValid(@Valid @RequestBody ValidModel model) {
        log.info("param = {}", model);
        return Res.success();
    }

    /**
     * curl -X POST -d '' http://localhost:18001/valid/form
     */
    @PostMapping("/valid/form")
    public Res<Object> testValid(@Valid @RequestParam String name) {
        log.info("name = {}", name);
        return Res.success();
    }

    /**
     * curl -X POST 'http://localhost:18001/valid/custom_err
     */
    @PostMapping("/valid/custom_err")
    public Res<Object> testMyException() {
        throw new ParamException("my error msg");
    }
}
