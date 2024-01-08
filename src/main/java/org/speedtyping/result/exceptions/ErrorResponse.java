package org.speedtyping.result.exceptions;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
public class ErrorResponse {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public ErrorResponse(Integer status, String error, String message, String path) {
        timestamp = LocalDateTime.now(ZoneOffset.UTC);
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}