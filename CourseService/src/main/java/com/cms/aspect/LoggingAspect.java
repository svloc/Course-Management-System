
package com.cms.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {
    @Pointcut("execution(* com.cms.service.CourseServiceImpl.*(..))")
    public void serviceMethods() {
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logSuccess(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info("The method {} has completed successfully.", methodName);
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
    public void logFailure(JoinPoint joinPoint, Exception exception) {
        log.error(exception.getMessage());
    }
}
