package com.myarxiv.myarxiv.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.myarxiv.myarxiv.common.RedisUtils;
import com.myarxiv.myarxiv.common.ResponseResult;

import com.myarxiv.myarxiv.common.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Resource
    private RedisUtils redisUtils;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
//        查看redis中是否存在token，如果不存在直接跳转到登陆页面
//        String token = request.getHeader("Authorization");
////        Object o = redisUtils.get(token);
////        System.out.println("userId:"+o);
//        if (!redisUtils.hasKey(token)) {
//            log.info("过期或者不存在的token:",token);
//            String jsonObj = JSONObject.toJSONString(ResponseResult.fail("你没有token或者你的token已失效", StatusCode.UNAUTHORIZED.getCode()));
//            returnJson(response,jsonObj);
//            return false;
//        }
//        //刷新token有效期
//        redisUtils.expire(token, 1, TimeUnit.HOURS);
        return true;
    }

    private void returnJson(HttpServletResponse response, String json) throws Exception{
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("json/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }


}
