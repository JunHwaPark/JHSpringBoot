package com.junhwa.springboot.web;

import com.google.gson.Gson;
import com.junhwa.springboot.domain.user.User;
import com.junhwa.springboot.service.TradeService;
import com.junhwa.springboot.service.UserService;
import com.junhwa.springboot.web.dto.TradeListResponseDto;
import com.junhwa.springboot.web.dto.TradeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TradeController {
    private final TradeService tradeService;
    private final UserService userService;
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

    @GetMapping("/api/v1/trade")
    public String loadAllTrades() {
        List<TradeListResponseDto> tradeListResponseDtoList =  tradeService.findAllDesc();
        return gson.toJson(tradeListResponseDtoList);
    }
}
