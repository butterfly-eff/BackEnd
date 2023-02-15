package com.example.butterflyeff.like.repository;

import com.example.butterflyeff.like.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByTradeIdAndMemberId(Long tradeId, Long memberId);
    Optional<Like> findAllByTradeId(Long tradeId);
    List<Like> findAllByMemberId(Long memberId);
}
