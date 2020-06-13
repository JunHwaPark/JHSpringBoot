package com.junhwa.springboot.domain.trade;

import com.junhwa.springboot.domain.BaseTimeEntity;
import com.junhwa.springboot.domain.location.LocationEntity;
import com.junhwa.springboot.domain.trade.transferrequest.TransferRequest;
import com.junhwa.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    @JoinColumn(name = "writer")
    private User writer;

    @OneToMany(mappedBy = "trade", cascade = CascadeType.ALL)
    private List<LocationEntity> locationEntities;

    @OneToMany(mappedBy = "trade", cascade = CascadeType.ALL)
    private List<TransferRequest> transferRequests;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Builder
    public Trade(User writer, Double distance, List<LocationEntity> locationEntities) {
        this.writer = writer;
        this.distance = distance;
        this.locationEntities = locationEntities;
        this.status = Status.DISPLAY;
    }

    public static double getDistanceMeter(LocationEntity entity1, LocationEntity entity2) {
        double theta = entity1.getLongitude() - entity2.getLongitude();
        double dist = Math.sin(Math.toRadians(entity1.getLatitude())) * Math.sin(Math.toRadians(entity2.getLatitude()))
                + Math.cos(Math.toRadians(entity1.getLatitude())) * Math.cos(Math.toRadians(entity2.getLatitude())) * Math.cos(Math.toRadians(theta));

        return Math.toDegrees(Math.acos(dist)) * 60 * 1.1515 * 1609.344;
    }
}
