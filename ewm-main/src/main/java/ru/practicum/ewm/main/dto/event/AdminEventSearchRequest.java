package ru.practicum.ewm.main.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.main.enums.EventState;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminEventSearchRequest {
    private List<Long> users;
    private EventState states;
    private List<Long> categories;
    private String rangeStart;
    private String rangeEnd;
    private Integer from;
    private Integer size;
}
