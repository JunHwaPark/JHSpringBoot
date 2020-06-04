package com.junhwa.springboot.config.auth.dto;

import com.junhwa.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private Long userId;
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}