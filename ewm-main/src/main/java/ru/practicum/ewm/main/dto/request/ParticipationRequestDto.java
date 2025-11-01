package ru.practicum.ewm.main.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.practicum.ewm.main.enums.RequestStatus;
import ru.practicum.ewm.main.util.DateFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormatter.PATTERN)
    private String created;

    @NotNull
    private Long eventId;

    @NotNull
    private Long requesterId;

    private RequestStatus status;
}
