package com.junhwa.springboot.web.dto;

import com.junhwa.springboot.domain.location.LocationEntity;
import com.junhwa.springboot.domain.trade.Trade;
import lombok.Getter;

@Getter
public class LocationEntityListResponseDto {
    private Long id;
    private double latitude, longitude;
    private String address;
    private Trade trade;

    public LocationEntityListResponseDto(LocationEntity entity) {
        this.id = entity.getId();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.address = entity.getAddress();
        this.trade = entity.getTrade();
    }
}