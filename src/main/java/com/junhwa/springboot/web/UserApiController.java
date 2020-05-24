package com.junhwa.springboot.web;

import com.junhwa.springboot.service.UserService;
import com.junhwa.springboot.web.dto.UserRegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;

    @PostMapping("/api/v1/user")
    public Long registerUser(@RequestBody UserRegisterRequestDto requestDto) {
        return userService.register(requestDto);
    }
}
