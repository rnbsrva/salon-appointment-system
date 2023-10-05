package com.akerke.authservice.payload.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StatusResponse {
    private Boolean success;
    private Object data;

    public StatusResponse(Boolean success) {
        this.success = success;
    }

    public static StatusResponse success() {
        return new StatusResponse(true);
    }

    public static StatusResponse success(Object data) {
        return new StatusResponse(true, data);
    }

    public static StatusResponse fail(Object data) {
        return new StatusResponse(false, data);
    }
}
