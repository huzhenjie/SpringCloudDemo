package com.scrat.background.module.aes;

import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class AESCipher {

    public static String encryptToBase64(String key, String ivParameter, String content) throws Exception {
        return encryptToBase64(key, ivParameter, content.getBytes(StandardCharsets.UTF_8));
    }

    public static String encryptToBase64(String key, String ivParameter, byte[] contentBytes) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = key.getBytes();
        SecretKeySpec sk = new SecretKeySpec(raw, "AES");

        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.ENCRYPT_MODE, sk, iv);
        byte[] encrypted = cipher.doFinal(contentBytes);
        return Base64Utils.encodeToString(encrypted);
    }

    public static String decryptStrFromBase64(String key, String ivParameter, String base64) throws Exception {
        byte[] original = decryptBytesFromBase64(key, ivParameter, base64);
        return new String(original, StandardCharsets.UTF_8);
    }

    public static byte[] decryptBytesFromBase64(String key, String ivParameter, String base64) throws Exception {
        byte[] raw = key.getBytes();
        SecretKeySpec sk = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.DECRYPT_MODE, sk, iv);
        byte[] encrypted1 = Base64Utils.decodeFromString(base64);
        return cipher.doFinal(encrypted1);
    }

    public static void main(String[] args) throws Exception {
        String key = "wfxAbPXiGMHRCdE7";
        String iv = "wfxAbPXiGMHRCdE7";
        String originContent = "Hello world";

        String encryptStr = encryptToBase64(key, iv, originContent);
        System.out.println(encryptStr);
        String decryptStr = decryptStrFromBase64(key, iv, encryptStr);
        System.out.println(decryptStr);

        Assert.isTrue(originContent.equals(decryptStr), "encrypt fail");

        System.out.println(decryptStrFromBase64(key, iv, "XdoG32sveCGtHJ861MYkEg=="));
    }
}
