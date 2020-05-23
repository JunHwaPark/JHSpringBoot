package com.junhwa.springboot.web.dto;

import com.junhwa.springboot.domain.posts.LocationEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LocationEntityListResponseDto {
    private Long id;
    private double latitude, longitude;
    private LocalDateTime modifiedDate;

    public LocationEntityListResponseDto(LocationEntity entity) {
        this.id = entity.getId();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.modifiedDate = entity.getModifiedDate();
    }
}
