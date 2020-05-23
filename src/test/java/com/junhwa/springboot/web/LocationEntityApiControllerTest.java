package com.junhwa.springboot.web;

import com.junhwa.springboot.domain.posts.LocationEntity;
import com.junhwa.springboot.domain.posts.LocationEntityRepository;
import com.junhwa.springboot.web.dto.LocationEntitySaveRequestDto;
import com.junhwa.springboot.web.dto.LocationEntityUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //@WebMvcTest는 JPA기능이 작동하지 않음.
public class LocationEntityApiControllerTest {
    private double latitude = 100d, longitude = 200d;

    @LocalServerPort
    private int port;

    @Autowired
    private LocationEntityRepository locationEntityRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @After
    public void tearDown() throws Exception {
        locationEntityRepository.deleteAll();
    }

    @Test
    public void registerPost() throws Exception {
        //given
        String title = "title";
        String content = "content";
        LocationEntitySaveRequestDto requestDto = LocationEntitySaveRequestDto.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();

        String url = "http://localhost:" + port + "/api/v1/location";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<LocationEntity> all = locationEntityRepository.findAll();
        assertThat(all.get(0).getLatitude()).isEqualTo(latitude);
        assertThat(all.get(0).getLongitude()).isEqualTo(longitude);
    }

    @Test
    public void updatePost() throws Exception {
        LocationEntity savedLocationEntity = locationEntityRepository.save(LocationEntity.builder()
                .latitude(latitude)
                .longitude(longitude)
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
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<LocationEntity> all = locationEntityRepository.findAll();
        assertThat(all.get(0).getLatitude()).isEqualTo(expectedLatitude);
        assertThat(all.get(0).getLongitude()).isEqualTo(expectedLongitude);
    }
}
