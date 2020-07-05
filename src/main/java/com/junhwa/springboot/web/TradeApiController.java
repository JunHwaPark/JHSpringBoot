package com.junhwa.springboot.web;

import com.google.gson.Gson;
import com.junhwa.springboot.domain.user.User;
import com.junhwa.springboot.service.TradeService;
import com.junhwa.springboot.service.TransferRequestService;
import com.junhwa.springboot.service.UserService;
import com.junhwa.springboot.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TradeApiController {
    private final TradeService tradeService;
    private final UserService userService;
    private final TransferRequestService transferRequestService;
    private final Gson gson;

    @PostMapping("/api/v1/trade")
    public Long save(@RequestBody TradeSaveRequestDto requestDto, Principal principal) {
        User writer = userService.findById(principal.getName());
        TradeSaveRequestDto saveRequestDto =
                TradeSaveRequestDto.builder()
                .start(requestDto.getStart())
                .end(requestDto.getEnd())
                .writer(writer)
                .build();
        return tradeService.save(saveRequestDto);
    }

    //TODO: 20200612 Test code 작성 필요
    @PostMapping("/api/v1/trade/request")
    public Long saveRequest(@RequestBody TransferRequestSaveDto requestSaveDto, Principal principal) {
        User deliverer = userService.findById(principal.getName());
        return transferRequestService.save(requestSaveDto.getTradeId(), deliverer);
    }

    //TODO: 20200612 Test code 작성 필요
    @GetMapping("/api/v1/trade/request/{id}")
    public String loadTransferRequestsByTradeId(@PathVariable Long id) {
        //TODO: 20200612 배달자 정보 클래스 따로 생성 필요.
        List<TransferRequestDto> transferRequestDtoList = transferRequestService.findByTradeId(id);
        return gson.toJson(transferRequestDtoList);
    }

    @GetMapping("/api/v1/trade")
    public String loadAllTrades() {
        List<TradeListResponseDto> tradeListResponseDtoList =  tradeService.findAllDesc();
        return gson.toJson(tradeListResponseDtoList);
    }

    @PutMapping("/api/v1/trade/request/{id}")
    public void acceptTransferRequest(@PathVariable Long id, @RequestBody TransferAcceptRequestDto transferAcceptRequestDto) {
        Long transferId = transferAcceptRequestDto.getTransferRequestId();
        tradeService.acceptTransferRequest(id, transferId);
    }
}
