package com.chensj.learn.learndemo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author chensj
 * @title 切面--日志切面
 * @package com.chensj.learn.learndemo.aop
 * @date: 2018-11-22 11:58
 */
@Aspect
@Component
public class LoggerAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    /**
     * service 服务层切面
     */
    @Pointcut("execution(* com.chensj.learn.learndemo.service.impl..*(..))")
    public void serviceAspect() {
    }

    /**
     * controller 控制层切面
     */
    @Pointcut("execution(* com.chensj.learn.learndemo.web..*(..))")
    public void controllerAscept() {
    }

    /**
     * dao 数据库切面
     */
    @Pointcut("execution(* com.chensj.learn.learndemo.dao..*(..))")
    public void daoAscept() {
    }

    /**
     * 调用方法前执行
     *
     * @param joinPoint
     */
    //@Before("serviceAspect()")
    public void serviceBeforeLogger(JoinPoint joinPoint) {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        logger.info("使用class:{},调用方法:{},参数：{}", targetName, methodName, arguments);
    }

    /**
     * 后置异常通知
     */
    //@AfterThrowing("serviceAspect()")
    public void serviceAfterThrowing(JoinPoint jp) {
        logger.info("方法异常时执行.....");
        System.out.println(jp);
    }

    /**
     * 方法调用返回参数时执行
     *
     * @param object
     */
    //@AfterReturning(returning = "object",pointcut = "serviceAspect()")
    public void doAfterReturn(Object object) {
        if (object == null) {
            logger.info(" doAfterReturn：no return obj");
        } else {
            //// 处理完请求，返回内容
            logger.info("doAfterReturn：response={}", object.toString());
        }
    }

    /**
     * 方法调用结束后执行
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     *
     * @param joinPoint
     */
    //@After("serviceAspect()")
    public void serviceAfterLogger(JoinPoint joinPoint) {
        logger.info("after aspect logger ");
    }


    //环绕通知,环绕增强，相当于MethodInterceptor
    @Around("serviceAspect()")
    public Object serviceAround(ProceedingJoinPoint pjp) {
        logger.info("方法环绕start.....");
        String targetName = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();
        Object[] arguments = pjp.getArgs();
        logger.info("使用class:{},调用方法:{},参数：{}", targetName, methodName, arguments);
        try {
            logger.info("方法执行开始.....");
            Object o = pjp.proceed();
            if (o == null) {
                logger.info("Around：no return obj");
            } else {
                //// 处理完请求，返回内容
                logger.info("Around：response={}", o.toString());
            }
            logger.info("方法执行完成.....");
            logger.info("方法环绕end.....");
            return o;
        } catch (Throwable e) {
            e.printStackTrace();
            logger.info("方法执行报错.....");
            logger.info("错误原因：{}", e.getMessage());
            logger.info("方法异常环绕end.....");
            return null;
        }
    }

    /**
     * 环绕--> 前置 --> 后置 --> 后置返回
     *                     --> 后置异常
     * 环绕会将异常捕捉，后置异常将不会生效
     */
    /**
     * @Aspect
     * 作用是把当前类标识为一个切面供容器读取
     * @Before
     * 标识一个前置增强方法，相当于BeforeAdvice的功能
     * @AfterReturning
     * 后置增强，相当于AfterReturningAdvice，方法退出时执行
     * @AfterThrowing
     * 异常抛出增强，相当于ThrowsAdvice
     * @After
     * final增强，不管是抛出异常或者正常退出都会执行
     * @Around
     * 环绕增强，相当于MethodInterceptor
     *
     */
}
