package com.tacbin.town.web.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-05 23:11
 **/
@Component
@Aspect
@Slf4j
public class AnalysisLogAspect {
    @Around("@annotation(AnalysisLog)")
    public Object count(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object obj = proceedingJoinPoint.proceed();
        if (proceedingJoinPoint instanceof MethodInvocationProceedingJoinPoint) {
//            log.info("{} 请求响应了{}毫秒", proceedingJoinPoint.getSignature().toString(), (System.currentTimeMillis() - start));
        }
        return obj;
    }
}
