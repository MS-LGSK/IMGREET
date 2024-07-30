package com.lgsk.imgreet.category.controller;

import com.lgsk.imgreet.category.dto.LocationDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {

	@GetMapping("/location")
	public String getLocation() {
		LocationDTO location = LocationDTO.builder()
			.latitude(37.582354)
			.longitude(127.001886)
			.build();
		return location.json();
	}
}
