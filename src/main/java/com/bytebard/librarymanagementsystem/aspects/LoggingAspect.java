package com.bytebard.librarymanagementsystem.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(public * com.bytebard.librarymanagementsystem.*.*(..))")
    private void loggingPointcut() {}

    @Before("loggingPointcut()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Before");
        logger.info("Calling method: {} with arguments: {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }
}
