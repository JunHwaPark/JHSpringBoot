package com.junhwa.springboot.domain.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationEntityRepository extends JpaRepository<LocationEntity, Long> {
    @Query("SELECT a from LocationEntity a order by a.id desc") //native query 쓰려면 빈 오버라이딩 켜야함!
    List<LocationEntity> findAllDesc();
}
