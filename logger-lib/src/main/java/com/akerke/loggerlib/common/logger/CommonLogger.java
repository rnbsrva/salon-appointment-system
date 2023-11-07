package com.akerke.loggerlib.common.logger;

import com.akerke.loggerlib.model.ExecutionTimeLog;
import com.akerke.loggerlib.model.Log;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.function.BiConsumer;

@Slf4j
@RequiredArgsConstructor
public class CommonLogger {

    private final String application;


    public void info(String message, ExecutionTimeLog logEntity) {
        logEntity.setApp(application);
        logEntity.addLogProperties();
        log.info(message, logEntity);
        MDC.clear();
    }
}
