package ru.practicum.ewm.main.dto.event;

import ru.practicum.ewm.main.entity.Location;
import ru.practicum.ewm.main.enums.AdminStateAction;

public class UpdateEventAdminDto {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private AdminStateAction stateAction;
    private String title;
}
