package com.junhwa.springboot.web;

import com.google.gson.Gson;
import com.junhwa.springboot.service.LocationEntityService;
import com.junhwa.springboot.service.UserService;
import com.junhwa.springboot.web.dto.LocationEntitySaveRequestDto;
import com.junhwa.springboot.web.dto.LocationEntityUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class LocationEntityApiController {
    private final LocationEntityService locationEntityService;
    private final UserService userService;
    private final Gson gson;

    @PostMapping("/api/v1/location")
    public Long save(@RequestBody LocationEntitySaveRequestDto requestDto, Principal principal) {
        LocationEntitySaveRequestDto saveRequestDto
                = LocationEntitySaveRequestDto.builder()
                .latitude(requestDto.getLatitude())
                .longitude(requestDto.getLongitude())
                .build();
        return locationEntityService.save(saveRequestDto);
    }

    @PutMapping("/api/v1/location/{id}")
    public Long update (@PathVariable Long id, @RequestBody LocationEntityUpdateRequestDto requestDto) {
        return locationEntityService.update(id, requestDto);
    }

    @GetMapping("/api/v1/location/{id}")
    public String findById (@PathVariable Long id) {
        return gson.toJson(locationEntityService.findById(id));
    }

    @GetMapping("/api/v1/trade/locations/{id}")
    public String findByTradeId(@PathVariable Long id) {
        return gson.toJson(locationEntityService.findByTradeId(id));
    }

    @DeleteMapping("/api/v1/location/{id}")
    public Long delete(@PathVariable Long id) {
        locationEntityService.delete(id);
        return id;
    }
}
