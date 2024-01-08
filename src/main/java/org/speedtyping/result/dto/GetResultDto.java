package org.speedtyping.result.dto;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class GetResultDto {
    private String ID;
    private String textUUID;
    private String userID;
    private List<Integer> symbolsPerSecond;
    private Integer symbolsPerMinute;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration resultTime;
    private Integer errorsCount;
    private Double accuracy;
}
