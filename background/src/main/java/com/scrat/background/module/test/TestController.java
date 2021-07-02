package com.scrat.background.module.test;

import com.scrat.background.model.Res;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @PostMapping("/test/json/{urlParam}")
    public Res<TestModel> testPostJson(@PathVariable String urlParam, @RequestBody TestModel model) {
        return Res.success(model).setMessage(urlParam);
    }

    @PostMapping("/test/form")
    public Res<String> testPostForm(@RequestParam String name) {
        return Res.success(name);
    }

    @GetMapping("/test/get")
    public Res<String> testGet(@RequestParam String name) {
        return Res.success(name);
    }
}
