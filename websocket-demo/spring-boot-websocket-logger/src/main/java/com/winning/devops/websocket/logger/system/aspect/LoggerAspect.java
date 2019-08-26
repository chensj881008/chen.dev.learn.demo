package com.winning.devops.websocket.logger.system.aspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chensj
 * @title logger aspect component
 * @project spring-boot-websocket-logger
 * @package com.winning.devops.websocket.logger.system.aspect
 * @date: 2019-01-31 16:33
 */
@Aspect
@Component
public class LoggerAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    /**
     * 定义切点
     */
    @Pointcut("execution(* com.winning.devops.websocket.logger..dao..*(..)) ||" +
            "execution(* com.winning.devops.websocket.logger..web..*(..)) ||" +
            "execution(* com.winning.devops.websocket.logger..service..*(..))")
    public void loggerErrorPointcut() {
    }

    /**
     * 定义切点
     */
    @Pointcut("execution(* com.winning.devops.websocket.logger..web..*(..))")
    public void loggerWebPointcut() {
    }

    /**
     * 环绕日志
     *
     * @param joinPoint
     */
    @Around(value = "loggerWebPointcut()")
    public void handleAround(JoinPoint joinPoint) {//DAO层抛出的异常在这边捕获
        List<String> params = initParams(joinPoint);
        logger.info("执行方法为：所在类：[{}],方法名称：[{}],参数:[{}],开始时间:[{}]", params.get(2), params.get(0), params.get(1), new SimpleDateFormat("YYYY-MM-DD HH:mm:ss").format(new Date()));
        try {
            ((ProceedingJoinPoint) joinPoint).proceed();
        } catch (Throwable e) {
            logger.error("异常所在类：[{}],异常所在方法：[{}],异常中的参数:[{}],异常:[{}]\n\r", params.get(2), params.get(0), params.get(1), e.getMessage());
        }
        logger.info("执行方法为：所在类：[{}],方法名称：[{}],参数:[{}],结束时间:[{}]", params.get(2), params.get(0), params.get(1), new SimpleDateFormat("YYYY-MM-DD HH:mm:ss").format(new Date()));
    }

    /**
     * 错误日志
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "loggerErrorPointcut()", throwing = "e")
    public void handleThrowing(JoinPoint joinPoint, Exception e) {//DAO层抛出的异常在这边捕获
        List<String> params = initParams(joinPoint);
        logger.info("异常所在类：[{}],异常所在方法：[{}],异常中的参数:[{}],异常:[{}]\n\r", params.get(2), params.get(0), params.get(1), e.getMessage());
    }

    /**
     * 参数封装
     *
     * @param joinPoint
     * @return
     */
    private List<String> initParams(JoinPoint joinPoint) {
        List<String> params = new ArrayList<>();
        String methodName = joinPoint.getSignature().getName();
        params.add(methodName);
        Object[] args = joinPoint.getArgs();
        params.add(JSON.toJSONString(args));
        String allName = joinPoint.getSignature().toString();
        allName = allName.split(" ")[1];
        //调用方法类名
        String className = allName.substring(0, allName.lastIndexOf("."));
        params.add(className);
        return params;
    }
}
