package com.scrat.background.module.encrypt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrat.background.model.Res;
import com.scrat.background.module.aes.AESCipher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class EncryptResponse implements ResponseBodyAdvice<Res<Object>> {
    private static Logger log = LoggerFactory.getLogger(EncryptResponse.class.getName());

    private final ObjectMapper mapper;
    private final EncryptProperties encryptProperties;

    public EncryptResponse(EncryptProperties encryptProperties) {
        this.encryptProperties = encryptProperties;
        this.mapper = new ObjectMapper();
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.hasMethodAnnotation(Encrypt.class);
    }

    @Override
    public Res<Object> beforeBodyWrite(
            Res<Object> body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {
        Object data = body.getData();
        if (data == null) {
            return body;
        }
        try {
            byte[] dataBytes = mapper.writeValueAsBytes(data);
            String encryptText = AESCipher.encryptToBase64(
                    encryptProperties.getKey(), encryptProperties.getIv(), dataBytes);
            body.setData(encryptText);
            return body;
        } catch (Exception e) {
            log.error("Encrypt Error", e);
            return Res.error(500, "Encrypt Error");
        }
    }
}
