package com.junhwa.springboot.web.dto;

import com.junhwa.springboot.domain.location.LocationEntity;
import lombok.Getter;

@Getter
public class LocationEntityResponseDto {
    private Long id;
    private double latitude, longitude;

    public LocationEntityResponseDto(LocationEntity entity) {
        this.id = entity.getId();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
    }
}
