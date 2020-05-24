package com.junhwa.springboot.domain.location;

import com.junhwa.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class LocationEntity extends BaseTimeEntity {

    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long id;

    @Column(nullable = false)
    private double latitude, longitude;

    @Column(nullable = false)
    private Long writer;

    @Builder
    public LocationEntity(double latitude, double longitude, Long writer) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.writer = writer;
    }

    public void update(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
