package com.junhwa.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junhwa.springboot.domain.location.LocationEntity;
import com.junhwa.springboot.domain.location.LocationEntityRepository;
import com.junhwa.springboot.domain.trade.TradeRepository;
import com.junhwa.springboot.domain.user.UserRepository;
import com.junhwa.springboot.service.LocationEntityService;
import com.junhwa.springboot.service.TradeService;
import com.junhwa.springboot.web.dto.*;
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
public class TradeApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private LocationEntityService locationEntityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private LocationEntityRepository locationEntityRepository;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;
    private String id = "testId", password = "testPassword", name = "testName";

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
        locationEntityRepository.deleteAll();
        tradeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void saveTrade() throws Exception {
        //given
        UserRegisterRequestDto userRegisterRequestDto = UserRegisterRequestDto.builder()
                .id(id)
                .password(password)
                .name(name)
                .build();
        String url = "http://localhost:" + port + "/api/v1/user";

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(userRegisterRequestDto)));

        SignInDto signInDto = SignInDto.builder()
                .username(id)
                .password(password)
                .build();

        url = "http://localhost:" + port + "/login";

        String token = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(signInDto)))
                .andReturn().getResponse().getContentAsString();

        TradeSaveRequestDto requestDto = TradeSaveRequestDto.builder()
                .start(LocationEntity.builder()
                        .latitude(38L)
                        .longitude(126L)
                        .address("test Address1")
                        .build())
                .end(LocationEntity.builder()
                        .latitude(37L)
                        .longitude(127L)
                        .address("test Address2")
                        .build())
                .build();
        url = "http://localhost:" + port + "/api/v1/trade";

        //when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto))
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        //then
        List<TradeListResponseDto> tradeListResponseDtos = tradeService.findAllDesc();
        assertThat(tradeListResponseDtos.get(0).getWriter()).isEqualTo(name);
        assertThat(tradeListResponseDtos.get(0).getDistance()).isGreaterThan(0D);
        List<LocationEntityResponseDto> locations = locationEntityService.findByTradeId(1L);
        assertThat(locations.get(0).getLatitude()).isEqualTo(38L);
        assertThat(locations.get(0).getLongitude()).isEqualTo(126L);
        assertThat(locations.get(0).getAddress()).isEqualTo("test Address1");
        assertThat(locations.get(1).getLatitude()).isEqualTo(37L);
        assertThat(locations.get(1).getLongitude()).isEqualTo(127L);
        assertThat(locations.get(1).getAddress()).isEqualTo("test Address2");
    }
}
