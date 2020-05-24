package com.junhwa.springboot.domain.user;

import com.junhwa.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;   //고유번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegisterType type;

    @Column(length = 20)
    private String id;      //자체회원 아이디

    @Column(length = 60)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column/*(nullable = false)*/
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, RegisterType type, String id, String password, String email, String picture, Role role) {
        this.name = name;
        this.type = type;
        this.id = id;
        this.password = type == RegisterType.SELF ? encryptPassword(password) : password;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String password, String picture, RegisterType type) {
        this.name = name;
        this.password = type == RegisterType.SELF ? encryptPassword(password) : password;
        this.picture = picture;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public static String encryptPassword(String password) {
        return (new BCryptPasswordEncoder()).encode(password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.role.name()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
