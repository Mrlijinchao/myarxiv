package com.myarxiv.myarxiv.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 异常处理方法
     * @return
     */
    @ExceptionHandler(SQLException.class)
    public Object exceptionHandler(SQLException exception){
        log.error("发生SQL异常:{}",exception.getMessage());
        return ResponseResult.fail("系统忙，请稍后重试...",StatusCode.ERROR.getCode());
    }


    @ExceptionHandler(Exception.class)
    public Object exceptionHandler(Exception exception){
        log.error("发生异常:{}",exception.getMessage());
        return ResponseResult.fail("系统忙，请稍后重试...",StatusCode.ERROR.getCode());
    }

}
