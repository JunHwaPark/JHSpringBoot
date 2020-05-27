package com.junhwa.springboot.service;

import com.junhwa.springboot.domain.user.Role;
import com.junhwa.springboot.domain.user.User;
import com.junhwa.springboot.domain.user.UserRepository;
import com.junhwa.springboot.web.dto.UserRegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Transactional
    public Long register(UserRegisterRequestDto requestDto) {
        return userRepository.save(requestDto.toEntity()).getUserId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username : " + username);
        Optional<User> userWrapper = userRepository.findById(username);
        User user = userWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(Role.USER.getKey()));

        return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(), authorities);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    public User findById(String id) {
        return userRepository.findById(id).get();
    }

    public User findByUserId(Long userId) {
        return userRepository.findById(userId).get();
    }
}
