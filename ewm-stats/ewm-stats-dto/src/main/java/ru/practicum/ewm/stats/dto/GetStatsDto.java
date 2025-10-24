package ru.practicum.ewm.stats.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class GetStatsDto {
    @NotNull
    private LocalDateTime start;

    @NotNull
    private LocalDateTime end;

    private List<String> uris;

    private Boolean unique;
}
