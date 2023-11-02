package com.akerke.loggerlib.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@Builder
public class Log {
    private final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    private String app;
    private String httpStatus;
    private String url;
    private String httpMethod;
    private Long execution;

    @Override
    public String toString() {
        return "timestamp=" + timestamp +
                ", app='" + app + '\'' +
                ", httpStatus='" + httpStatus + '\'' +
                ", url='" + url + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", execution=" + execution + " ms";
    }
}
