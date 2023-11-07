package com.akerke.loggerlib.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExecutionTimeLog {
    private String className;
    private String method;
    private long execution;
}
