package com.junhwa.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junhwa.springboot.domain.location.LocationEntity;
import com.junhwa.springboot.domain.location.LocationEntityRepository;
import com.junhwa.springboot.domain.user.RegisterType;
import com.junhwa.springboot.domain.user.Role;
import com.junhwa.springboot.domain.user.User;
import com.junhwa.springboot.domain.user.UserRepository;
import com.junhwa.springboot.web.dto.LocationEntitySaveRequestDto;
import com.junhwa.springboot.web.dto.LocationEntityUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //@WebMvcTest는 JPA기능이 작동하지 않음.
public class LocationEntityApiControllerTest {
    private double latitude = 100d, longitude = 200d;
    private Long writer = 1l;

    @LocalServerPort
    private int port;

    @Autowired
    private LocationEntityRepository locationEntityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @PostConstruct
    public void setUser() {
        userRepository.save(
                User.builder()
                        .id("testId")
                        .password("testPassword")
                        .name("testName")
                        //.email("testEmail")
                        .role(Role.USER)
                        .type(RegisterType.SELF)
                        .build());
    }

    @After
    public void tearDown() throws Exception {
        locationEntityRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    @WithUserDetails(value = "testId")      //MockMvc 에서만 작동한다. 따라서 SpringBootTest인 이 클래스에서는 mvc 인스턴스를 생성해서 사용한다.
    public void saveLocation() throws Exception {
        //given
        LocationEntitySaveRequestDto requestDto = LocationEntitySaveRequestDto.builder()
                .latitude(latitude)
                .longitude(longitude)
                .writer(writer)
                .build();

        String url = "http://localhost:" + port + "/api/v1/location";

        //when
        //ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class); //권한이 필요없을 때 사용
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
/*        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);*/
        List<LocationEntity> all = locationEntityRepository.findAll();
        assertThat(all.get(0).getLatitude()).isEqualTo(latitude);
        assertThat(all.get(0).getLongitude()).isEqualTo(longitude);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void updateLocation() throws Exception {
        //given
        LocationEntity savedLocationEntity = locationEntityRepository.save(LocationEntity.builder()
                .latitude(latitude)
                .longitude(longitude)
                .writer(writer)
                .build());

        Long updateId = savedLocationEntity.getId();
        double expectedLatitude = 111d, expectedLongitude = 222d;

        LocationEntityUpdateRequestDto requestDto = LocationEntityUpdateRequestDto.builder()
                .latitude(expectedLatitude)
                .longitude(expectedLongitude)
                .build();

        String url = "http://localhost:" + port + "/api/v1/location/" + updateId;

        HttpEntity<LocationEntityUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        //ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
/*        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);*/

        List<LocationEntity> all = locationEntityRepository.findAll();
        assertThat(all.get(0).getLatitude()).isEqualTo(expectedLatitude);
        assertThat(all.get(0).getLongitude()).isEqualTo(expectedLongitude);
    }
}
