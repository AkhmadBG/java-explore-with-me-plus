package ru.practicum.ewm.main.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewm.main.dto.category.CategoryDto;
import ru.practicum.ewm.main.dto.user.UserShortDto;
import ru.practicum.ewm.main.entity.Location;

import static ru.practicum.ewm.main.util.DateFormatter.PATTERN;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDto {
    private Long id;
    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PATTERN)
    private String eventDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PATTERN)
    private String createdOn;

    private UserShortDto initiator;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PATTERN)
    private String publishedOn;

    private Boolean requestModeration;
    private String state;
    private String title;
    private Long views;
}
