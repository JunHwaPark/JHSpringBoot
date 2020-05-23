package com.junhwa.springboot.web;

import com.junhwa.springboot.config.auth.LoginUser;
import com.junhwa.springboot.config.auth.dto.SessionUser;
import com.junhwa.springboot.service.LocationEntityService;
import com.junhwa.springboot.web.dto.LocationEntityResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final LocationEntityService locationEntityService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user/*, Principal principal*/) {
        model.addAttribute("locationEntity", locationEntityService.findAllDesc());
        SecurityContextHolder securityContextHolder = new SecurityContextHolder();
        //TODO: 20200524 비소셜로그인 세션 받아오기 필요
/*        if (principal != null)
            System.out.println(principal.getName());*/
        if (user != null)
            model.addAttribute("userName", user.getName());

        return "index";
    }

    @GetMapping("/location/save")
    public String postsSave() {
        return "location-save";
    }

    @GetMapping("/location/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        LocationEntityResponseDto dto = locationEntityService.findById(id);
        model.addAttribute("locationEntity", dto);
        return "location-update";
    }

    @GetMapping("/user/signup")
    public String registerUser() {
        return "user-signup";
    }

    @GetMapping("/user/login")
    public String login() {
        return "user-login";
    }
}
