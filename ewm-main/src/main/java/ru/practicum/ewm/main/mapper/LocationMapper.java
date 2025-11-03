package ru.practicum.ewm.main.mapper;

import ru.practicum.ewm.main.dto.event.LocationDto;
import ru.practicum.ewm.main.entity.Location;

public class LocationMapper {

    public static LocationDto locationToDto(Location location) {
        return LocationDto.builder()
                .id(location.getId())
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }

    public static Location toLocation(LocationDto locationDto) {
        return Location.builder()
                .id(locationDto.getId())
                .lat(locationDto.getLat())
                .lon(locationDto.getLon())
                .build();
    }

}