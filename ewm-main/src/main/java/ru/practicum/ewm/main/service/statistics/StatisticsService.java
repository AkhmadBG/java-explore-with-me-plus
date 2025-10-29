package ru.practicum.ewm.main.service.statistics;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.ewm.main.entity.Event;
import ru.practicum.ewm.stats.dto.ViewStats;

import java.util.List;

public interface StatisticsService {

    List<ViewStats> getStats(String start, String end, List<String> uris);

    void sendStat(Event event, HttpServletRequest request);

    void sendStat(List<Event> events, HttpServletRequest request);

    void setView(Event event);

    void setView(List<Event> event);
}
