package com.junhwa.springboot.domain.location;

import com.junhwa.springboot.domain.user.RegisterType;
import com.junhwa.springboot.domain.user.Role;
import com.junhwa.springboot.domain.user.User;
import com.junhwa.springboot.domain.user.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationEntityRepositoryTest {
    private double latitude = 100d, longitude = 200d;
    private User writer;

    @Autowired
    LocationEntityRepository locationEntityRepository;

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void setUser() {
        userRepository.save(
                writer = User.builder()
                        .id("testId")
                        .password("testPassword")
                        .name("testName")
                        //.email("testEmail")
                        .role(Role.USER)
                        .type(RegisterType.SELF)
                        .build());
    }

    @After
    public void cleanup() {
        locationEntityRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void saveNLoadLocation() {
        //given
        locationEntityRepository.save(LocationEntity.builder()
                .latitude(latitude)
                .longitude(longitude)
                .writer(writer)
                .build());

        //when
        List<LocationEntity> locationEntityList = locationEntityRepository.findAll();

        //then
        LocationEntity locationEntity = locationEntityList.get(0);
        assertThat(locationEntity.getLatitude()).isEqualTo(latitude);
        assertThat(locationEntity.getLongitude()).isEqualTo(longitude);
    }

    @Test
    public void registerBaseTimeEntity() {
        //given
        LocalDateTime now = LocalDateTime.of(2020, 5, 13, 0, 0, 0);
        locationEntityRepository.save(LocationEntity.builder()
                .latitude(latitude)
                .longitude(longitude)
                .writer(writer)
                .build());

        //when
        List<LocationEntity> locationEntityList = locationEntityRepository.findAll();

        //then
        LocationEntity locationEntity = locationEntityList.get(0);

        System.out.println(">>>>>>>>>>>>> createDate=" + locationEntity.getCreatedDate() + ", modifiedDate=" + locationEntity.getModifiedDate());

        assertThat(locationEntity.getCreatedDate()).isAfter(now);
        assertThat(locationEntity.getModifiedDate()).isAfter(now);
    }
}
