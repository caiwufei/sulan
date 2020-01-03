package com.yilanjiaju.sulan.common.utils;

import com.yilanjiaju.sulan.common.exception.ChatEncryptException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Created with IDEA
 *
 * @description: 聊天内容加密工具
 * @author: xubo
 * @create: 2018-11-28 15:19
 */
@Slf4j
public class ChatEncryptUtil {

    //聊天消息加解密的密码
    private static final String CHAT_ENCRYPT_PASSWORD = "AppleTree2018";

    /**
     * 聊天内容加密
     *
     * @param plaintext 明文
     * @return Base64编码后的加密数据
     * @throws ChatEncryptException
     */
    public static String encode(String plaintext) throws ChatEncryptException {
        try {
            if (StringUtils.isNotEmpty(plaintext)) {
                return AESUtil.encrypt(plaintext, CHAT_ENCRYPT_PASSWORD);
            }
            return "";
        } catch (Exception e) {
            log.error("chat content encrypt error", e);
            throw new ChatEncryptException("chat content encrypt error", e);
        }
    }

    /**
     * 聊天内容解密
     *
     * @param ciphertext Base64编码后的密文
     * @return 解密后的明文
     * @throws ChatEncryptException
     */
    public static String decode(String ciphertext) throws ChatEncryptException {
        try {
            //todo 临时判断，如果待解密的文本中含有<MsgContent>标签，说明是明文，直接返回
            if (StringUtils.isNotEmpty(ciphertext) && ciphertext.contains("<MsgContent>")) {
                return ciphertext;
            }

            if (StringUtils.isNotEmpty(ciphertext)) {
                return AESUtil.decrypt(ciphertext, CHAT_ENCRYPT_PASSWORD);
            }
            return "";
        } catch (Exception e) {
            log.error("chat content encrypt error", e);
            throw new ChatEncryptException("chat content encrypt error", e);
        }
    }

}
