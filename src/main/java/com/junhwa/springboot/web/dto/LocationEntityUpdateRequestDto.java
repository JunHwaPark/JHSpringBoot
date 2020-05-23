package com.junhwa.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LocationEntityUpdateRequestDto {
    private double latitude, longitude;

    @Builder
    public LocationEntityUpdateRequestDto(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
