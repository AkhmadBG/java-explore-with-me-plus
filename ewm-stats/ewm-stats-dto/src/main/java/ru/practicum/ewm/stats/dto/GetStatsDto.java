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
    LocalDateTime start;

    @NotNull
    LocalDateTime end;

    List<String> uris;

    Boolean unique;
}
