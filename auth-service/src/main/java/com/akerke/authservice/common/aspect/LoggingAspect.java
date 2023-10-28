package com.akerke.authservice.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.akerke.authservice.controller..*(..))")
    public Object logMethodExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        var methodSignature = (MethodSignature) pjp.getSignature();

        final var stopWatch = new StopWatch();

        stopWatch.start();
        var result = pjp.proceed();
        stopWatch.stop();

        log.info(
                "%s.%s :: %d ms".formatted(
                        methodSignature.getDeclaringType().getSimpleName(), // Class Name
                        methodSignature.getName(), // Method Name
                        stopWatch.getTime(TimeUnit.MILLISECONDS) // execution time in milliseconds
                )
        );

        return result;
    }

}
