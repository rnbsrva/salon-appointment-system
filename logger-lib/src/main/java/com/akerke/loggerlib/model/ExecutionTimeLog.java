package com.akerke.loggerlib.model;

import lombok.Builder;
import lombok.Data;
import org.slf4j.MDC;

@Data
@Builder
public class ExecutionTimeLog {
    private String className;
    private String method;
    private long execution;
    private String app;

    public void addLogProperties() {
        MDC.put("className", className);
        MDC.put("method", method);
        MDC.put("execution", Long.toString(execution));
        MDC.put("app", app);
    }

}
