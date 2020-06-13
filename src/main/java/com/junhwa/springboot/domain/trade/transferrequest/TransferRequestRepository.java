package com.junhwa.springboot.domain.trade.transferrequest;

import com.junhwa.springboot.domain.trade.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Junhwa Park
 * @email wnsghk1025@naver.com
 * @created 2020-06-11
 */
public interface TransferRequestRepository extends JpaRepository<TransferRequest, Long> {
    List<TransferRequest> findByTrade(Trade trade);
}
