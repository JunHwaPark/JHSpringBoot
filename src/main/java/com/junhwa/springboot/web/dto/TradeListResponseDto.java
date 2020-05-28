package com.junhwa.springboot.web.dto;

import com.junhwa.springboot.domain.trade.Trade;
import lombok.Getter;

@Getter
public class TradeListResponseDto {
    private Long id;
    private String writer;

    public TradeListResponseDto(Trade trade) {
        this.id = trade.getId();
        this.writer = trade.getWriter().getName();
    }
}
