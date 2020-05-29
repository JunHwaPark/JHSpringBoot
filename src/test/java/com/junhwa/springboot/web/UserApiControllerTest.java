package com.junhwa.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junhwa.springboot.domain.user.User;
import com.junhwa.springboot.domain.user.UserRepository;
import com.junhwa.springboot.web.dto.SignInDto;
import com.junhwa.springboot.web.dto.UserRegisterRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApiControllerTest {
    private String id = "testId", password = "password", name = "test";

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }

    //@Test
    public void registerUser() throws Exception{
        //given
        UserRegisterRequestDto userRegisterRequestDto = UserRegisterRequestDto.builder()
                .id(id)
                .password(password)
                .name(name)
                .build();

        String url = "http://localhost:" + port + "/api/v1/user";

        //when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(userRegisterRequestDto)))
                .andExpect(status().isOk());

        //then
        List<User> all = userRepository.findAll();
        assertThat(all.get(0).getId()).isEqualTo(id);
        assertThat(all.get(0).getName()).isEqualTo(name);
    }

    @Test
    public void getAccessToken() throws Exception {
        //given
        registerUser();
        SignInDto signInDto = SignInDto.builder()
                .username(id)
                .password(password)
                .build();

        String url = "http://localhost:" + port + "/login";

        //when
        String token = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(signInDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        //then
        assertThat(token.length()).isGreaterThan(0);
    }
}
