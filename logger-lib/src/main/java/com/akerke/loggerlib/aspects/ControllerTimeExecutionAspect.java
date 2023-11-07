package com.akerke.loggerlib.aspects;

import com.akerke.loggerlib.logger.CommonLogger;
import com.akerke.loggerlib.model.ExecutionTimeLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
public class ControllerTimeExecutionAspect {

    private final CommonLogger commonLogger;

    public ControllerTimeExecutionAspect(CommonLogger commonLogger) {
        this.commonLogger = commonLogger;
    }

    @Around("execution(* com.akerke..controller..*(..))")
    public Object logMethodExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        var methodSignature = (MethodSignature) pjp.getSignature();

        final var stopWatch = new StopWatch();

        stopWatch.start();
        var result = pjp.proceed();
        stopWatch.stop();

        commonLogger.info(
                "%s.%s :: %d ms".formatted(
                        methodSignature.getDeclaringType().getSimpleName(), // Class Name
                        methodSignature.getName(), // Method Name
                        stopWatch.getLastTaskTimeMillis() // execution time in milliseconds
                ), ExecutionTimeLog
                        .builder()
                        .method(methodSignature.getName())
                        .execution(stopWatch.getLastTaskTimeMillis())
                        .className(methodSignature.getDeclaringType().getSimpleName())
                        .build()
        );

        return result;
    }
}
