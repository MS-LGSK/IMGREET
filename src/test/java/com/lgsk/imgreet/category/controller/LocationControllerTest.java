package com.lgsk.imgreet.category.controller;

import com.lgsk.imgreet.category.dto.LocationDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LocationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getLocationTest() throws Exception {
		LocationDTO location = LocationDTO.builder()
			.latitude(37.582354)
			.longitude(127.001886)
			.build();
		String expectedJson = location.json();

		mockMvc.perform(get("/location"))
			.andExpect(status().isOk())
			.andExpect(content().json(expectedJson));
	}
}
