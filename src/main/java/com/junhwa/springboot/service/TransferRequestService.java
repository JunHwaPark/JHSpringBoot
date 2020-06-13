package com.junhwa.springboot.service;

import com.junhwa.springboot.domain.trade.TradeRepository;
import com.junhwa.springboot.domain.trade.transferrequest.TransferRequest;
import com.junhwa.springboot.domain.trade.transferrequest.TransferRequestRepository;
import com.junhwa.springboot.domain.user.User;
import com.junhwa.springboot.web.dto.TransferRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Junhwa Park
 * @email wnsghk1025@naver.com
 * @created 2020-06-11
 */

@RequiredArgsConstructor
@Service
public class TransferRequestService {
    private final TransferRequestRepository transferRequestRepository;
    private final TradeRepository tradeRepository;

    @Transactional
    public Long save(Long tradeId, User deliverer) {
        return transferRequestRepository.save(TransferRequest.builder()
                .trade(tradeRepository.findById(tradeId).get())
                .deliverer(deliverer)
                .build()).getId();
    }

    @Transactional(readOnly = true)
    public List<TransferRequestDto> findByTradeId(Long id) {
        return transferRequestRepository.findByTrade(tradeRepository.findById(id).get()).stream()
                .map(TransferRequestDto::new)
                .collect(Collectors.toList());
    }
}
