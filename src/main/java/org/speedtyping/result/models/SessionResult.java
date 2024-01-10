package org.speedtyping.result.models;

import lombok.Data;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class SessionResult implements Serializable {
    private String codeExampleUUID;
    private List<Integer> symbolsPerSecond;
    private Integer symbolsPerMinute;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration resultTime;
    private Integer errorsCount;
    private Double accuracy;
}
