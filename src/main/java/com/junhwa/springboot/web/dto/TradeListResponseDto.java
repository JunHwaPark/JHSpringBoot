package com.junhwa.springboot.web.dto;

import com.junhwa.springboot.domain.trade.Status;
import com.junhwa.springboot.domain.trade.Trade;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TradeListResponseDto {
    private Long id;
    private String writer;
    private Double distance;
    private Status status;
    private LocalDateTime modifiedTime;

    public TradeListResponseDto(Trade trade) {
        this.id = trade.getId();
        this.writer = trade.getWriter().getName();
        this.distance = trade.getDistance();
        this.status = trade.getStatus();
        this.modifiedTime = trade.getModifiedDate();
    }
}