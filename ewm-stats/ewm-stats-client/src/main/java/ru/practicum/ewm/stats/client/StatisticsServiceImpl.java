package ru.practicum.ewm.stats.client;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.ewm.main.entity.Event;
import ru.practicum.ewm.main.service.statistics.StatisticsService;
import ru.practicum.ewm.stats.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.practicum.ewm.main.util.DateFormatter.format;

public class StatisticsServiceImpl implements StatisticsService {
    @Override
    public List<ViewStats> getStats(String start, String end, List<String> uris) {
        return List.of();
    }

    @Override
    public void sendStat(Event event, HttpServletRequest request) {

    }

    @Override
    public void sendStat(List<Event> events, HttpServletRequest request) {
        events.forEach(event -> sendStat(events, request));
    }

    @Override
    public void setView(Event event) {
        setView(List.of(event));
    }

    @Override
    public void setView(List<Event> events) {
        if (events == null || events.isEmpty()) return;

        LocalDateTime start = events.stream()
                .map(Event::getCreatedOn)
                .filter(Objects::nonNull)
                .min(LocalDateTime::compareTo)
                .orElse(LocalDateTime.now().minusYears(1));

        List<String> uris = events.stream()
                .map(event -> "/events/" + event.getId())
                .collect(Collectors.toList());

        List<ViewStats> stats = getStats(format(start), format(LocalDateTime.now()), uris);

        Map<String, Long> viewsMap = stats.stream()
                .collect(Collectors.toMap(
                        ViewStats::getUri,
                        ViewStats::getHits,
                        (existing, replacement) -> existing));

        for (Event event : events) {
            String uri = "/events/" + event.getId();
            event.setViews(viewsMap.getOrDefault(uri, 0L));
        }
    }
}
