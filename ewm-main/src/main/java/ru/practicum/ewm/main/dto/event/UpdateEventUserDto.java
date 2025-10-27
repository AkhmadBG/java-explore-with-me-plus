package ru.practicum.ewm.main.dto.event;

import ru.practicum.ewm.main.entity.Location;
import ru.practicum.ewm.main.enums.UserStateAction;

public class UpdateEventUserDto {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private UserStateAction stateAction;
    private String title;
}
