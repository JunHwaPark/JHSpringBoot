package com.junhwa.springboot.web.dto;

import com.junhwa.springboot.domain.posts.LocationEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LocationEntitySaveRequestDto {
    private double latitude, longitude;

    @Builder
    public LocationEntitySaveRequestDto(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationEntity toEntity() {
        return LocationEntity.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
