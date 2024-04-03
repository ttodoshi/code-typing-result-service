package org.codetyping.result.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "results")
@Data
public class Result {
    @Id
    private String ID;
    private String codeExampleUUID;
    private String userID;
    private List<Integer> symbolsPerSecond;
    private Integer symbolsPerMinute;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Transient
    private Duration resultTime;
    private Integer errorsCount;
    private Double accuracy;

    public Duration getResultTime() {
        return Duration.between(startTime, endTime);
    }
}
