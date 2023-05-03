package com.myarxiv.myarxiv.common;

import lombok.Data;

import java.util.HashMap;

@Data
public class ResponseResult {

    private Object message; //返回消息

    private HashMap meta; // 返回状态

    public static Object success(Object obj){
        ResponseResult result = new ResponseResult();
        result.message = obj;
        result.meta = new HashMap<>();
        result.meta.put("msg","请求成功");
        result.meta.put("status",StatusCode.SUCCESS.getCode());
        return result;
    }

    public static Object returnWithToken(Object obj, String token){
        ResponseResult result = new ResponseResult();
        result.message = obj;
        result.meta = new HashMap<>();
        result.meta.put("msg","请求成功");
        result.meta.put("status",StatusCode.SUCCESS.getCode());
        result.meta.put("token",token);
        return result;
    }

    public static Object fail(String msg,Integer statusCode){
        ResponseResult result = new ResponseResult();
        result.meta = new HashMap<>();
        result.meta.put("msg",msg);
        result.meta.put("status",statusCode);
        return result;
    }

}
