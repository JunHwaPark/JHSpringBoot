package com.junhwa.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationEntityRepository extends JpaRepository<LocationEntity, Long> {
    @Query(value = "select * from LOCATION_ENTITY order by id desc", nativeQuery=true)
    List<LocationEntity> findAllDesc();
}
