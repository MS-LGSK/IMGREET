package com.lgsk.imgreet.category.controller;

import com.lgsk.imgreet.category.dto.LocationDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {

    @GetMapping("/location")
    public String getLocation() {
        return new LocationDTO(37.582354, 127.001886).json();
    }
}
