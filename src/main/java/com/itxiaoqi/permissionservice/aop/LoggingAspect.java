package com.itxiaoqi.permissionservice.aop;

import cn.hutool.json.JSONUtil;
import com.itxiaoqi.permissionservice.anno.Loggable;
import com.itxiaoqi.permissionservice.constant.Constant;
import com.itxiaoqi.permissionservice.entity.po.OperationLog;
import com.itxiaoqi.permissionservice.mapper.UserRoleMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Aspect
@Component
public class LoggingAspect {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private UserRoleMapper userRoleMapper;

    @Pointcut("@annotation(com.itxiaoqi.permissionservice.anno.Loggable)")
    public void logPointcut() {}

    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String ip=getClientIp(request);
        String params= JSONUtil.toJsonStr(joinPoint.getArgs());
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String operation = signature.getMethod().getAnnotation(Loggable.class).value();
        Long operatorId=Long.parseLong(joinPoint.getArgs()[0].toString());
        Long userId=Long.parseLong(joinPoint.getArgs()[1].toString());
        OperationLog operationLog = OperationLog.builder()
                .operatorId(operatorId)
                .operation(operation)
                .ip(ip)
                .requestParams(params)
                .createTime(LocalDateTime.now())
                .build();
        try {
            boolean flag = signature.getMethod().getAnnotation(Loggable.class).flag();
            Map<String,Object> mp=new HashMap<>();
            if(flag){
                mp.put("old",userRoleMapper.selectById(userId));
            }
            Object result = joinPoint.proceed();
            if(flag){
                mp.put("new",userRoleMapper.selectById(userId));
            }
            saveLog(operationLog,true,mp.toString(),"");
            return result;
        } catch (Exception e) {
            saveLog(operationLog,false,"",e.getMessage());
            throw e;
        }
    }

    private void saveLog(OperationLog operationLog,Boolean operationStatus,String detail,String errorMessage){
        operationLog.setOperationStatus(operationStatus);
        operationLog.setDetail(detail);
        operationLog.setErrorMessage(errorMessage);
        rabbitTemplate.convertAndSend(Constant.LOGGING_EXCHANGE,
                Constant.LOGGING_ROUTING,
                operationLog);
    }

    /**
     * AI-Deepseek
     * @param request
     * @return
     */
    public String getClientIp(HttpServletRequest request) {
        String ipAddress = null;

        ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress != null && !ipAddress.isEmpty() && !"unknown".equalsIgnoreCase(ipAddress)) {
            int index = ipAddress.indexOf(',');
            if (index != -1) {
                ipAddress = ipAddress.substring(0, index).trim();
            }
            return ipAddress;
        }

        ipAddress = request.getHeader("Proxy-Client-IP");
        if (ipAddress != null && !ipAddress.isEmpty() && !"unknown".equalsIgnoreCase(ipAddress)) {
            return ipAddress;
        }

        ipAddress = request.getHeader("WL-Proxy-Client-IP");
        if (ipAddress != null && !ipAddress.isEmpty() && !"unknown".equalsIgnoreCase(ipAddress)) {
            return ipAddress;
        }

        ipAddress = request.getHeader("HTTP_CLIENT_IP");
        if (ipAddress != null && !ipAddress.isEmpty() && !"unknown".equalsIgnoreCase(ipAddress)) {
            return ipAddress;
        }

        ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (ipAddress != null && !ipAddress.isEmpty() && !"unknown".equalsIgnoreCase(ipAddress)) {
            return ipAddress;
        }

        ipAddress = request.getHeader("X-Real-IP");
        if (ipAddress != null && !ipAddress.isEmpty() && !"unknown".equalsIgnoreCase(ipAddress)) {
            return ipAddress;
        }

        ipAddress = request.getRemoteAddr();

        if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
            return "127.0.0.1";
        }

        return ipAddress;
    }

}