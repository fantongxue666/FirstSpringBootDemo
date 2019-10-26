package com.qianlong.exception;

public class UserNotExistException extends RuntimeException {
    public UserNotExistException() {
        super("该用户不存在");
    }
}
