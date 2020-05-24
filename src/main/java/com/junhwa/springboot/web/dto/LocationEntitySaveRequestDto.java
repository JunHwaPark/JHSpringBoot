package com.junhwa.springboot.web.dto;

import com.junhwa.springboot.domain.location.LocationEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LocationEntitySaveRequestDto {
    private Long writer;
    private double latitude, longitude;

    @Builder
    public LocationEntitySaveRequestDto(double latitude, double longitude, Long writer) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.writer = writer;
    }

    public LocationEntity toEntity() {
        return LocationEntity.builder()
                .latitude(latitude)
                .longitude(longitude)
                .writer(writer)
                .build();
    }
}
