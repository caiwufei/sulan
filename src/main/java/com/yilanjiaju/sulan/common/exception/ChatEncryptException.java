package com.yilanjiaju.sulan.common.exception;

/**
 * Created with IDEA
 *
 * @description: 聊天记录加解密异常
 * @author: xubo
 * @create: 2018-11-28 15:32
 */
public class ChatEncryptException extends Exception {

    private static final String DEFAULT_MESSAGE = "chat content encrypt or decrypt fail.";

    public ChatEncryptException() {
        super(DEFAULT_MESSAGE);
    }

    public ChatEncryptException(String message) {
        super(message);
    }

    public ChatEncryptException(String message, Throwable cause) {
        super(message, cause);
    }
}
