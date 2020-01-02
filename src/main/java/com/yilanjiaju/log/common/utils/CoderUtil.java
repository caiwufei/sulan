package com.yilanjiaju.log.common.utils;


import org.apache.commons.codec.binary.Hex;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class CoderUtil {

    public static String SHAEncrypt(String key) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(key.getBytes());
        return Hex.encodeHexString(md.digest());
    }

    public static String SHAEncrypt(String key, String salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update((key + salt).getBytes());
        return Hex.encodeHexString(md.digest());
    }

    public static String MD5Encrypt(String key) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] md5Bytes = md.digest(key.getBytes());
        return Hex.encodeHexString(md5Bytes);
    }

    public static String MD5Encrypt(String key, String salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] md5Bytes = md.digest((key + salt).getBytes());
        return Hex.encodeHexString(md5Bytes);
    }

    public static String BASE64encode(String key) throws Exception {
        BASE64Encoder encoder = new BASE64Encoder();
        String encode = encoder.encode(key.getBytes());
        return encode;
    }

    public static String BASE64decode(String encodeKey) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        return new String(decoder.decodeBuffer(encodeKey));
    }


    public static String DESEncrypt(String data, String key) throws Exception {
        String encryptedData = null;
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        DESKeySpec deskey = new DESKeySpec(key.getBytes());
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(deskey);
        // 加密对象
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
        // 加密，并把字节数组编码成字符串
        encryptedData = new BASE64Encoder().encode(cipher.doFinal(data.getBytes()));

        return encryptedData;
    }


    public static String DESDecrypt(String cryptData, String key) throws Exception {
        String decryptedData = null;
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        DESKeySpec desKey = new DESKeySpec(key.getBytes());
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKey);
        // 解密对象
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
        // 把字符串解码为字节数组，并解密
        decryptedData = new String(cipher.doFinal(new BASE64Decoder().decodeBuffer(cryptData)));
        return decryptedData;
    }


}
