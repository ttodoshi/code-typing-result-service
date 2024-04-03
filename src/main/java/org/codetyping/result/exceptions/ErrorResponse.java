package org.codetyping.result.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Clock;
import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'Z'")
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public ErrorResponse(Integer status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now(Clock.systemUTC());
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
