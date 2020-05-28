package com.junhwa.springboot.domain.trade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    @Query("SELECT a from Trade a order by a.id desc ")
    List<Trade> findAllDesc();
}
