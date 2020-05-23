package com.junhwa.springboot.domain.posts;

import com.junhwa.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class LocationEntity extends BaseTimeEntity {

    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long id;

    @Column(nullable = false)
    private double latitude, longitude;

    @Builder
    public LocationEntity(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void update(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
