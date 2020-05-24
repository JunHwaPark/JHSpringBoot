package com.junhwa.springboot.web.dto;

import com.junhwa.springboot.domain.location.LocationEntity;
import lombok.Getter;

@Getter
public class LocationEntityResponseDto {
    private Long id, writer;
    private double latitude, longitude;

    public LocationEntityResponseDto(LocationEntity entity) {
        this.id = entity.getId();
        this.writer = entity.getWriter();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
    }
}
