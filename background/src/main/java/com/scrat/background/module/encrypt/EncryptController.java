package com.scrat.background.module.encrypt;

import com.scrat.background.model.Res;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EncryptController {

    @Encrypt
    @GetMapping("/encrypt")
    public Res<String> getEncryptStrResult() {
        return Res.success("Hello world");
    }

    @Encrypt
    @GetMapping("/encrypt/map")
    public Res<Map<String, Object>> getEncryptMapResult() {
        Map<String, Object> data = new HashMap<>();
        data.put("123", 123);
        return Res.success(data);
    }

    @Encrypt
    @GetMapping("/encrypt/int")
    public Res<Integer> getEncryptIntResult() {
        return Res.success(1024);
    }

    // curl -X POST -H 'Content-Type:application/json' -d 'nFdng5S+QiovwXSmWfs0cg==' http://localhost:18001/decrypt
    @Decrypt
    @PostMapping("/decrypt")
    public Res<Object> decryptParam(@RequestBody Map<String, Object> body) {
        return Res.success(body);
    }
}
