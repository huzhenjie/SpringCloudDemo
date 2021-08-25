package com.scrat.background.module.sha1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1Util {
    private static final char[] HEX = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String bytesToHexStr(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            buf.append(HEX[(aByte >> 4) & 0x0f]);
            buf.append(HEX[aByte & 0x0f]);
        }
        return buf.toString();
    }

    public static String sha1Digest(String content) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
        messageDigest.update(content.getBytes());
        byte[] sha1Bytes = messageDigest.digest();
        return bytesToHexStr(sha1Bytes);
    }
}
