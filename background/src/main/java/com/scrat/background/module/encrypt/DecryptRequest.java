package com.scrat.background.module.encrypt;

import com.scrat.background.module.aes.AESCipher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

@ControllerAdvice
public class DecryptRequest extends RequestBodyAdviceAdapter {
    private static Logger log = LoggerFactory.getLogger(DecryptRequest.class.getName());
    private final EncryptProperties encryptProperties;

    public DecryptRequest(EncryptProperties encryptProperties) {
        this.encryptProperties = encryptProperties;
    }

    @Override
    public boolean supports(MethodParameter methodParameter,
                            Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasMethodAnnotation(Decrypt.class)
                || methodParameter.hasParameterAnnotation(Decrypt.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage,
                                           MethodParameter parameter,
                                           Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        try {
            byte[] body = new byte[inputMessage.getBody().available()];
            int bytesCnt = inputMessage.getBody().read(body);
            if (bytesCnt <= 0) {
                return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
            }
            byte[] decrypt = AESCipher.decryptBytesFromBase64(
                    encryptProperties.getKey(), encryptProperties.getIv(), new String(body, StandardCharsets.UTF_8));
            return new HttpInputMessage() {
                @Override
                public InputStream getBody() throws IOException {
                    return new ByteArrayInputStream(decrypt);
                }

                @Override
                public HttpHeaders getHeaders() {
                    return inputMessage.getHeaders();
                }
            };
        } catch (Exception e) {
            log.error("Decrypt fail", e);
            return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
        }
    }
}
