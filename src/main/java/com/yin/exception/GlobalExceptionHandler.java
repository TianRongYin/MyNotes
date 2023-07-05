package com.yin.exception;

import com.yin.domain.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotLoginException.class)//token拦截器
    public Result doException(NotLoginException ex){
        return Result.fail(ex.getCode(),ex.getMessage());
    }
    @ExceptionHandler(ServiceException.class)//业务层拦截器
    public Result doException(ServiceException ex){
        return Result.fail(ex.getCode(),ex.getMessage());
    }

}
