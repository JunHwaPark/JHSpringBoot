package com.junhwa.springboot.domain.trade;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    DISPLAY("DISPLAY", "표기중"),
    TRANSFER_BUY("TRANSFER_BUY", "상점 이동중"),
    TRANSFER_TO_ORDERER("TRANSFER_TO_ORDERER", "배달중"),
    END("END", "배달 완료"),
    CALCULATED("CALCULATED", "정산 완료");

    private final String key;
    private final String title;
}
