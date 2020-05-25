package com.junhwa.springboot.web.dto;

import com.junhwa.springboot.domain.location.LocationEntity;
import com.junhwa.springboot.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LocationEntityListResponseDto {
    private Long id;
    private User writer;
    private double latitude, longitude;
    private LocalDateTime modifiedDate;

    public LocationEntityListResponseDto(LocationEntity entity) {
        this.id = entity.getId();
        this.writer = entity.getWriter();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.modifiedDate = entity.getModifiedDate();
    }
}
