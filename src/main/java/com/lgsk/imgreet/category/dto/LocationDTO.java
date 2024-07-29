package com.lgsk.imgreet.category.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LocationDTO {
    private final double latitude;
    private final double longitude;

    public String json() {
        return String.format("{\"latitude\": %f, \"longitude\": %f}", latitude, longitude);
    }
}
