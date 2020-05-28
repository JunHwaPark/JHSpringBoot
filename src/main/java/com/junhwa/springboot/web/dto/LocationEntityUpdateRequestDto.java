package com.junhwa.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LocationEntityUpdateRequestDto {
    private double latitude, longitude;
    private String address;

    @Builder
    public LocationEntityUpdateRequestDto(double latitude, double longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }
}
