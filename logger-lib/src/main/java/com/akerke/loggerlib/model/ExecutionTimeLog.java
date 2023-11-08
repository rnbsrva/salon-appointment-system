package com.akerke.loggerlib.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.MDC;

@Getter
@Setter
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

    @Override
    public String toString() {
        return "class_name='" + className + '\'' +
                ", method='" + method + '\'' +
                ", execution=" + execution + " ms"  +
                ", app='" + app;
    }
}
