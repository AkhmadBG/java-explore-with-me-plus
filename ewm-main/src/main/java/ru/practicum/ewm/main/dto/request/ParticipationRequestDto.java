package ru.practicum.ewm.main.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static ru.practicum.ewm.main.util.DateFormatter.PATTERN;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PATTERN)
    private String created;

    @NotNull
    private Long event;

    private Long id;

    @NotNull
    private Long requester;

    private String status;
}
