package com.junhwa.springboot.web;

import com.junhwa.springboot.config.auth.LoginUser;
import com.junhwa.springboot.config.auth.dto.SessionUser;
import com.junhwa.springboot.service.LocationEntityService;
import com.junhwa.springboot.web.dto.LocationEntityResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final LocationEntityService locationEntityService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("locationEntity", locationEntityService.findAllDesc());
        if (user != null) {
            System.out.println("Login : " + user.getName());
            model.addAttribute("userName", user.getName());
        }
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
