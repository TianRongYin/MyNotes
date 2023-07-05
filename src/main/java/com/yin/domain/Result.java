package com.yin.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    private Object data;
    private Integer code;
    private String message;

    public static Result success(String message){return new Result(null,200,message);}
    public static Result success(Object data,String message){return new Result(data,200,message);}
    public static Result fail(String message){return new Result(null,500,message);}
    public static Result fail(Integer code, String message){return new Result(null,code,message);}
}
