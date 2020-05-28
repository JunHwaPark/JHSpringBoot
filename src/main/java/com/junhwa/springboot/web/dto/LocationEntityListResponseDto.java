package com.junhwa.springboot.web.dto;

import com.junhwa.springboot.domain.location.LocationEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LocationEntityListResponseDto {
    private Long id;
    private double latitude, longitude;
    private String address;
    private LocalDateTime modifiedDate;

    public LocationEntityListResponseDto(LocationEntity entity) {
        this.id = entity.getId();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.address = entity.getAddress();
        this.modifiedDate = entity.getModifiedDate();
    }
}
