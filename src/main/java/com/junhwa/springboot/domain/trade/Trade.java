package com.junhwa.springboot.domain.trade;

import com.junhwa.springboot.domain.BaseTimeEntity;
import com.junhwa.springboot.domain.location.LocationEntity;
import com.junhwa.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Trade extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double distance;

    @ManyToOne(optional = false)
    private User writer;

    @Builder
    public Trade(User writer, Double distance) {
        this.writer = writer;
        this.distance = distance;
    }

    public static double getDistanceMeter(LocationEntity entity1, LocationEntity entity2) {
        double theta = entity1.getLongitude() - entity2.getLongitude();
        double dist = Math.sin(Math.toRadians(entity1.getLatitude())) * Math.sin(Math.toRadians(entity2.getLatitude()))
                + Math.cos(Math.toRadians(entity1.getLatitude())) * Math.cos(Math.toRadians(entity2.getLatitude())) * Math.cos(Math.toRadians(theta));

        return Math.toDegrees(Math.acos(dist)) * 60 * 1.1515 * 1609.344;
    }
}
