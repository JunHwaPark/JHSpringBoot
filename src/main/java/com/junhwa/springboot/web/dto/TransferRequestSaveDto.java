package com.junhwa.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Junhwa Park
 * @email wnsghk1025@naver.com
 * @created 2020-06-12
 */

@Getter
@NoArgsConstructor
public class TransferRequestSaveDto {
    private Long tradeId;

    @Builder
    public TransferRequestSaveDto(Long tradeId) {
        this.tradeId = tradeId;
    }
}
