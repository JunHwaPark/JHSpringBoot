package com.junhwa.springboot.web;

import com.google.gson.Gson;
import com.junhwa.springboot.service.LocationEntityService;
import com.junhwa.springboot.web.dto.LocationEntityResponseDto;
import com.junhwa.springboot.web.dto.LocationEntitySaveRequestDto;
import com.junhwa.springboot.web.dto.LocationEntityUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LocationEntityApiController {
    private final LocationEntityService locationEntityService;
    private final Gson gson;

    @PostMapping("/api/v1/location")
    public Long save(@RequestBody LocationEntitySaveRequestDto requestDto) {
        return locationEntityService.save(requestDto);
    }

    @PutMapping("/api/v1/location/{id}")
    public Long update (@PathVariable Long id, @RequestBody LocationEntityUpdateRequestDto requestDto) {
        return locationEntityService.update(id, requestDto);
    }

    @GetMapping("/api/v1/location/{id}")
    public LocationEntityResponseDto findById (@PathVariable Long id) {
        return locationEntityService.findById(id);
    }

    @DeleteMapping("/api/v1/location/{id}")
    public Long delete(@PathVariable Long id) {
        locationEntityService.delete(id);
        return id;
    }
}
