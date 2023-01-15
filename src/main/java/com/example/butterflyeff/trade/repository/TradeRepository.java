package com.example.butterflyeff.trade.repository;

import com.example.butterflyeff.trade.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {
}
