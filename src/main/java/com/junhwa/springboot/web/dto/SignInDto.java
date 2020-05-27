package com.junhwa.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class SignInDto {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @Builder
    public SignInDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
