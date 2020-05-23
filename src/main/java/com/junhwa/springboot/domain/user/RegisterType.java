package com.junhwa.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RegisterType {
    GOOGLE("USER_GOOGLE", "구글"),
    NAVER("USER_NAVER", "네이버"),
    SELF("USER_SELF", "자체");

    private final String key;
    private final String title;
}
