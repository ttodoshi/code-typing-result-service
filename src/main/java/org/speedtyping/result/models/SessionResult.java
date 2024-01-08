package org.speedtyping.result.models;

import lombok.Data;

import java.io.Serializable;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class SessionResult implements Serializable {
    private String textUUID;
    private List<Integer> symbolsPerSecond;
    private Integer symbolsPerMinute;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private Duration resultTime;
    private Integer errorsCount;
    private Double accuracy;
}
