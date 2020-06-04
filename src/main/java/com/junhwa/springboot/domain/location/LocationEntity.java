package com.junhwa.springboot.domain.location;

import com.junhwa.springboot.domain.trade.Trade;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class LocationEntity {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long id;

    @Column(nullable = false)
    private double latitude, longitude;

    @Column
    private String address;

    @ManyToOne(optional = false)
    @JoinColumn(name = "trade_id")
    private Trade trade;

    @Builder
    public LocationEntity(double latitude, double longitude, String address, Trade trade) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.trade = trade;
    }

    public void update(double latitude, double longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }
}
