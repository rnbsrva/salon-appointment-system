package com.akerke.loggerlib.common.aspect;

import com.akerke.loggerlib.common.logger.CommonLogger;
import com.akerke.loggerlib.model.ExecutionTimeLog;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StopWatch;

@Aspect
@RequiredArgsConstructor
public class EnableLoggerLibTimeExecutionAspect {


    private final CommonLogger commonLogger;

    @Pointcut("within(@com.akerke.loggerlib.common.annotation.EnableLoggerLib *) && execution(* *(..))")
    public void enableAuthLogging() {

    }

    @Around("@within(com.akerke.loggerlib.common.annotation.EnableLoggerLib)")
    public Object logMethodExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        var methodSignature = (MethodSignature) pjp.getSignature();

        final var stopWatch = new StopWatch();

        stopWatch.start();
        var result = pjp.proceed();
        stopWatch.stop();

        commonLogger.info(
                "Log {}",
                ExecutionTimeLog
                        .builder()
                        .method(methodSignature.getName())
                        .execution(stopWatch.getLastTaskTimeMillis())
                        .className(methodSignature.getDeclaringType().getSimpleName())
                        .build()
        );

        return result;
    }
}
