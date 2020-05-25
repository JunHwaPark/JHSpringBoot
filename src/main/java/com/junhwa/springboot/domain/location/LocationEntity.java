package com.junhwa.springboot.domain.location;

import com.junhwa.springboot.domain.BaseTimeEntity;
import com.junhwa.springboot.domain.user.User;
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

    @ManyToOne
    private User writer;

    @Builder
    public LocationEntity(double latitude, double longitude, User writer) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.writer = writer;
    }

    public void update(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
