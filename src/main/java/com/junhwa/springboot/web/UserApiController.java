package com.junhwa.springboot.web;

import com.junhwa.springboot.config.auth.JwtProvider;
import com.junhwa.springboot.service.UserService;
import com.junhwa.springboot.web.dto.SignInDto;
import com.junhwa.springboot.web.dto.UserRegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/api/v1/user")
    public Long registerUser(@RequestBody UserRegisterRequestDto requestDto) {
        return userService.register(requestDto);
    }

    @PostMapping("/login")
    public String signIn(@RequestBody @Valid SignInDto signInDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword()));
            return jwtProvider.createToken(signInDto.getUsername());
        } catch (AuthenticationException e) {
            System.out.println("Log in failed for user, " + signInDto.getUsername());
        }

        return "";
    }
}
