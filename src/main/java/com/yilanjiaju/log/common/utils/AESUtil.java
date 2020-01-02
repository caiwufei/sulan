package com.yilanjiaju.log.common.utils;

import com.yilanjiaju.log.common.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created with IDEA
 *
 * @description: aes对称加密工具类
 * @author: xubo
 * @create: 2018-11-28 14:50
 */
@Slf4j
public class AESUtil {

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法

    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @param password 加密密码
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(final String content,final String password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器
        byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));// 初始化为加密模式的密码器
        byte[] result = cipher.doFinal(byteContent);// 加密
        return Base64.encodeBase64String(result);//通过Base64转码返回
//        return parseByte2HexStr(result);//二进制数组转16进制字符串返回
    }

    /**
     * AES 解密操作
     *
     * @param content
     * @param password
     * @return
     */
    public static String decrypt(final String content, final String password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);//实例化
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));//使用密钥初始化，设置为解密模式
        byte[] result = cipher.doFinal(Base64.decodeBase64(content));//执行操作
//        byte[] result = cipher.doFinal(parseHexStr2Byte(content));//执行操作
        return new String(result, StandardCharsets.UTF_8);
    }

    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @param password 加密密码
     * @return 返回hex转码后的加密数据
     */
    public static String encryptHex(final String content,final String password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器
        byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));// 初始化为加密模式的密码器
        byte[] result = cipher.doFinal(byteContent);// 加密
//        return Base64.encodeBase64String(result);//通过Base64转码返回
        return parseByte2HexStr(result);//二进制数组转16进制字符串返回
    }

    /**
     * AES 解密操作
     *
     * @param content
     * @param password
     * @return 返回解密密数据
     */
    public static String decryptHex(final String content, final String password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);//实例化
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));//使用密钥初始化，设置为解密模式
//        byte[] result = cipher.doFinal(Base64.decodeBase64(content));//执行操作
        byte[] result = cipher.doFinal(parseHexStr2Byte(content));//执行操作
        return new String(result, StandardCharsets.UTF_8);
    }

    /**
     * 生成加密秘钥
     *
     * @return
     */
    private static SecretKeySpec getSecretKey(final String password) throws NoSuchAlgorithmException {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(password.getBytes());
        //AES 要求密钥长度为 128
        kg.init(128, secureRandom);
        //生成一个密钥
        SecretKey secretKey = kg.generateKey();
        return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
    }

    /**将二进制转换成16进制
     * @param buf
     * @return
     */
    private static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return new byte[0];
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        try {
           String aa = AESUtil.encrypt("123456", Constant.ENCODE_KEY);
           System.out.println(aa);
        }catch (Exception e){

        }

    }

}
