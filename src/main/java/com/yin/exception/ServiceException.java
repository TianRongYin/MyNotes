package com.yin.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException{
    private Integer code;

    public ServiceException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}
