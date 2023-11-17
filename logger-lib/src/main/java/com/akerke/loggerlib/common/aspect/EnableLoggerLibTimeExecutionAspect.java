package com.akerke.loggerlib.common.aspect;

import com.akerke.loggerlib.model.ExecutionTimeLog;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StopWatch;


@Aspect
@RequiredArgsConstructor
public class EnableLoggerLibTimeExecutionAspect {

    private final String application;

    private final static Logger logger = LoggerFactory.getLogger(EnableLoggerLibTimeExecutionAspect.class);


    @Around("@within(com.akerke.loggerlib.common.annotation.EnableLoggerLib)")
    public Object logMethodExecutionTime(
            ProceedingJoinPoint pjp
    ) throws Throwable {
        var methodSignature = (MethodSignature) pjp.getSignature();

        final var stopWatch = new StopWatch();

        stopWatch.start();
        var result = pjp.proceed();
        stopWatch.stop();

        var executionTimeLog = ExecutionTimeLog
                .builder()
                .method(methodSignature.getName())
                .app(application)
                .execution(stopWatch.getLastTaskTimeMillis())
                .className(methodSignature.getDeclaringType().getSimpleName())
                .build();

        executionTimeLog.addLogProperties();

        logger.info("Log {}", executionTimeLog);

        MDC.clear();

        return result;
    }
}
