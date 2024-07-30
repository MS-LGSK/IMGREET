package com.lgsk.imgreet.category.dto;

import lombok.*;

@Getter
@Builder
public class LocationDTO {
    private double latitude;
    private double longitude;

    public String json() {
        return String.format("{\"latitude\": %f, \"longitude\": %f}", latitude, longitude);
    }
}
