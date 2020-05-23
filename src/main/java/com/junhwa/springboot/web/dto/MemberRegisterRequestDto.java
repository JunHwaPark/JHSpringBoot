package com.junhwa.springboot.web.dto;

import com.junhwa.springboot.domain.user.User;
import com.junhwa.springboot.domain.user.RegisterType;
import com.junhwa.springboot.domain.user.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRegisterRequestDto {
    //private RegisterType type;
    private String id, password;

    @Builder
    public MemberRegisterRequestDto(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public User toEntity() {
        return User.builder()
                .type(RegisterType.SELF)
                .id(id)
                .password(password)
                .name("test")
                .role(Role.USER)
                .build();
    }
}
