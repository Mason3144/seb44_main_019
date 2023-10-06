package com.shellwe.back.repository;

import com.shellwe.back.entity.Shell;
import com.shellwe.back.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    Optional<Trade> findByBuyerShellAndSellerShell(Shell buyerShell, Shell sellerShell);
}
