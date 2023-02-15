package com.example.butterflyeff.trade.repository;

import com.example.butterflyeff.member.model.Member;
import com.example.butterflyeff.trade.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    Optional<Trade> findByEmail(String email);
}
