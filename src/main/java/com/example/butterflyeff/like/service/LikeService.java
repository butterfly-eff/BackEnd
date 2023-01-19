package com.example.butterflyeff.like.service;


import com.example.butterflyeff.like.model.Like;
import com.example.butterflyeff.like.repository.LikeRepository;
import com.example.butterflyeff.member.dto.response.ResponseDto;
import com.example.butterflyeff.security.auth.UserDetailsImpl;
import com.example.butterflyeff.trade.model.Trade;
import com.example.butterflyeff.trade.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final TradeRepository tradeRepository;

    // 좋아요 추가, 삭제
    @Transactional
    public ResponseDto<String>addLike(UserDetailsImpl userDetails, Long id){
        Optional<Like> likeRepo = likeRepository.findByTradeIdAndMemberId(id, userDetails.getMember().getId());
        Optional<Trade> trade = tradeRepository.findById(id);

        // 좋아요 있으면 -> 삭제
        if(likeRepo.isPresent()){
            likeRepository.delete(likeRepo.get());

            return ResponseDto.success("좋아요 취소");
        // 좋아요 없으면 -> 추가
        }else{
            Like like = Like.builder()
                    .member(userDetails.getMember())
                    .trade(trade.get())
                    .build();

            likeRepository.save(like);

            return ResponseDto.success("좋아요 추가");
        }
    }

}
