package com.junhwa.springboot.web.dto;

import com.junhwa.springboot.domain.location.LocationEntity;
import com.junhwa.springboot.domain.trade.Trade;
import com.junhwa.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TradeSaveRequestDto {
    private LocationEntity start, end;
    private User writer;

    @Builder
    public TradeSaveRequestDto(LocationEntity start, LocationEntity end, User writer) {
        this.start = start;
        this.end = end;
        this.writer = writer;
    }

    public Trade toEntity() {
        return Trade.builder()
                .start(start)
                .end(end)
                .writer(writer)
                .build();
    }
}
