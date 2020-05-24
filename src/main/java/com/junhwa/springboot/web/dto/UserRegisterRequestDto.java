package com.junhwa.springboot.web.dto;

import com.junhwa.springboot.domain.user.User;
import com.junhwa.springboot.domain.user.RegisterType;
import com.junhwa.springboot.domain.user.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRegisterRequestDto {
    private String id, password, name;

    @Builder
    public UserRegisterRequestDto(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

    public User toEntity() {
        return User.builder()
                .type(RegisterType.SELF)
                .id(id)
                .password(password)
                .name(name)
                .role(Role.USER)
                .build();
    }
}
