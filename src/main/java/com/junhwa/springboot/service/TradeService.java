package com.junhwa.springboot.service;

import com.junhwa.springboot.domain.location.LocationEntity;
import com.junhwa.springboot.domain.location.LocationEntityRepository;
import com.junhwa.springboot.domain.trade.Trade;
import com.junhwa.springboot.domain.trade.TradeRepository;
import com.junhwa.springboot.domain.trade.transferrequest.TransferRequest;
import com.junhwa.springboot.domain.trade.transferrequest.TransferRequestRepository;
import com.junhwa.springboot.web.dto.TradeListResponseDto;
import com.junhwa.springboot.web.dto.TradeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TradeService {
    private final TradeRepository tradeRepository;
    private final LocationEntityRepository locationEntityRepository;
    private final TransferRequestRepository transferRequestRepository;

    @Transactional
    public Long save(TradeSaveRequestDto requestDto) {
        Trade trade = requestDto.toEntity();
        //거래 먼저 저장
        Long result = tradeRepository.save(trade).getId();

        //위치 정보 저장 (외래키로 거래 지정)
        locationEntityRepository.save(LocationEntity.builder()
                .latitude(requestDto.getStart().getLatitude())
                .longitude(requestDto.getStart().getLongitude())
                .address(requestDto.getStart().getAddress())
                .trade(trade)
                .build());
        locationEntityRepository.save(LocationEntity.builder()
                .latitude(requestDto.getEnd().getLatitude())
                .longitude(requestDto.getEnd().getLongitude())
                .address(requestDto.getEnd().getAddress())
                .trade(trade)
                .build());
        return result;
    }

    @Transactional(readOnly = true)
    public List<TradeListResponseDto> findAllDesc() {
        return tradeRepository.findAllDesc().stream()
                .map(TradeListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void acceptTransferRequest(Long tradeId, Long requestId) {
        Trade trade = tradeRepository.findById(tradeId).get();
        TransferRequest transferRequest = transferRequestRepository.findById(requestId).get();
        trade.acceptTransferRequest(transferRequest);
    }
}
