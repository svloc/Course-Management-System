package com.cms.aspect;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

// import org.aspectj.lang.JoinPoint;
// import org.aspectj.lang.annotation.AfterReturning;
// import org.aspectj.lang.annotation.AfterThrowing;
 import org.aspectj.lang.annotation.Aspect;
// import org.aspectj.lang.annotation.Before;
// import org.aspectj.lang.annotation.Pointcut;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

//   @Pointcut("execution(* com.cms.service.*(..))")
//   public void serviceMethods() {}

//   @Before("serviceMethods()")
//   public void logBefore(JoinPoint joinPoint) {
//     log.info("Entering method: {}", joinPoint.getSignature().toShortString());
//   }

//   @AfterReturning(value = "serviceMethods()", returning = "result")
//   public void logAfterReturning(JoinPoint joinPoint, Object result) {
//     log.info("The method {} has completed successfully", joinPoint.getSignature().toShortString());
//   }

//   @AfterThrowing(value = "serviceMethods()", throwing = "exception")
//   public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
//     log.error("Exception in method {}: {}", joinPoint.getSignature().toShortString(), exception.getMessage());
//   }
}
