package com.junhwa.springboot.domain.trade.transferrequest;

import com.junhwa.springboot.domain.trade.Trade;
import com.junhwa.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class TransferRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "deliverer_id")
    private User deliverer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "trade_id")
    private Trade trade;

    @OneToOne
    @JoinColumn(name = "accepted")
    private Trade acceptedTrade;

    @Builder
    public TransferRequest(User deliverer, Trade trade) {
        this.deliverer = deliverer;
        this.trade = trade;
    }
}
