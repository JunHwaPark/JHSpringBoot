package com.junhwa.springboot.web.dto;

import com.junhwa.springboot.domain.trade.transferrequest.TransferRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Junhwa Park
 * @email wnsghk1025@naver.com
 * @created 2020-06-11
 */

@Getter
@NoArgsConstructor
public class TransferRequestDto {
    private Long trade;
    private Long deliverer;

    public TransferRequestDto(TransferRequest transferRequest) {
        this.trade = transferRequest.getTrade().getId();
        this.deliverer = transferRequest.getDeliverer().getUserId();
    }
}
