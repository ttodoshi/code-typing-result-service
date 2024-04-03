package org.codetyping.result.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateResultDto {
    private String codeExampleUUID;
    private List<Integer> symbolsPerSecond;
    private Integer symbolsPerMinute;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer errorsCount;
    private Double accuracy;
}
