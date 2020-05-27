package com.junhwa.springboot.web;

import com.google.gson.Gson;
import com.junhwa.springboot.config.auth.LoginUser;
import com.junhwa.springboot.config.auth.dto.SessionUser;
import com.junhwa.springboot.service.LocationEntityService;
import com.junhwa.springboot.service.UserService;
import com.junhwa.springboot.web.dto.LocationEntitySaveRequestDto;
import com.junhwa.springboot.web.dto.LocationEntityUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LocationEntityApiController {
    private final LocationEntityService locationEntityService;
    private final UserService userService;
    private final Gson gson;

    @PostMapping("/api/v1/location")
    public Long save(@RequestBody LocationEntitySaveRequestDto requestDto, @LoginUser SessionUser sessionUser) {
        //TODO: 20200527 프로토타입 개발을 위한 임시방편! 추후 JWT적용 요망
/*        LocationEntitySaveRequestDto saveRequestDto
                = LocationEntitySaveRequestDto.builder()
                .latitude(requestDto.getLatitude())
                .longitude(requestDto.getLongitude())
                .writer(userService.findByUserId(sessionUser.getUserId()))
                .build();*/
        return locationEntityService.save(requestDto);
    }

    @PutMapping("/api/v1/location/{id}")
    public Long update (@PathVariable Long id, @RequestBody LocationEntityUpdateRequestDto requestDto) {
        return locationEntityService.update(id, requestDto);
    }

    @GetMapping("/api/v1/location/{id}")
    public String findById (@PathVariable Long id) {
        return gson.toJson(locationEntityService.findById(id));
    }

    @DeleteMapping("/api/v1/location/{id}")
    public Long delete(@PathVariable Long id) {
        locationEntityService.delete(id);
        return id;
    }
}
