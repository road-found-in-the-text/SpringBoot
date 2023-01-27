package com.example.umc3_teamproject.config;

import java.security.MessageDigest;

public class EncryptionUtils {

    public static String encryptSHA256(String s) { // 이게 더 강력함
        return encrypt(s, "SHA-256");
    }

    public static String encryptMD5(String s) {
        return encrypt(s, "MD5");
    }

    public static String encrypt(String s, String messageDigest) {
        // messageDigest 파라미터
        // 이 파라미터는 암호화 알고리즘을 지정한다
        try {
            MessageDigest md = MessageDigest.getInstance(messageDigest);
            //SHA-256 or MD5 알고리즘 지정가능
            byte[] passBytes = s.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digested.length; i++)
                sb.append(Integer.toHexString(0xff & digested[i]));
            return sb.toString();
        } catch (Exception e) {
            return s;
        }
    }

}
