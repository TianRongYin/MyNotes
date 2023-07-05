package com.yin.exception;

import lombok.Getter;

@Getter
public class NotLoginException extends RuntimeException{
    private Integer code;
    public NotLoginException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
