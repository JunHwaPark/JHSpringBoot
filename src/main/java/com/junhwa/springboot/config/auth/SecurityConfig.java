package com.junhwa.springboot.config.auth;

import com.junhwa.springboot.domain.user.Role;
import com.junhwa.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final UserService userService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and().authorizeRequests()
                .antMatchers("/admin").hasRole(Role.ADMIN.name())   //관리자 페이지는 관리자만 허용
                .antMatchers("/user/login", "/user/signup", "/api/v1/user").anonymous()        //회원가입은 익명 사용자만 허용
                //TODO: 20200527 프로토타입 개발을 위한 임시방편! 추후 JWT적용 요망
                //.antMatchers("/api/v1/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name()) //api사용은 유저 이상만 허용
                .anyRequest().permitAll()
                .and().formLogin().loginPage("/user/login").defaultSuccessUrl("/").permitAll()
                .and().logout().logoutSuccessUrl("/")
                .and().oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }
}