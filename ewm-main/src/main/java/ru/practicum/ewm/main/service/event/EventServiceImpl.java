package ru.practicum.ewm.main.service.event;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.entity.Event;

@Service
public class EventServiceImpl {
    public Event getEventById(Long id) {
        return new Event();
    }
}
