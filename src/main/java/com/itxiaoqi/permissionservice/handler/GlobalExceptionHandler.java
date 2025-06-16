package com.itxiaoqi.permissionservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 捕获系统异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    public String exceptionHandler(RuntimeException e){
        log.error("异常信息：{}", e.getMessage());
//        e.printStackTrace();
        return "";
    }

}
