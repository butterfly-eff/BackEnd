package com.example.butterflyeff.trade.service;

import com.example.butterflyeff.member.dto.response.ResponseDto;
import com.example.butterflyeff.security.auth.UserDetailsImpl;
import com.example.butterflyeff.trade.dto.TradeRequestDto;
import com.example.butterflyeff.trade.dto.TradeResponseDto;
import com.example.butterflyeff.trade.model.Trade;
import com.example.butterflyeff.trade.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TradeService {
    private final TradeRepository tradeRepository;

    //물물교환 전체조회
    public ResponseDto<List<TradeResponseDto>> getAllTrade(UserDetailsImpl userDetails) {
        List<TradeResponseDto> tradeResponseDtoList = new ArrayList<>();
        List<Trade> tradeList = tradeRepository.findAll();

        for (Trade trade : tradeList) {
            tradeResponseDtoList.add(
                    TradeResponseDto.builder()
                            .id(trade.getId())
                            .imgURL(trade.getImgURL())
                            .expiryDate(trade.getExpiryDate())
                            .state(trade.getState())
                            .title(trade.getTitle())
//                            .likeCnt(tradeLike.getLikeCnt)
//                            .like(trade)

                            .build()
            );
        }
        return ResponseDto.success(tradeResponseDtoList);
    }

    //물물교환 상품 상세조회
    public ResponseDto<TradeResponseDto> getTrade(Long id){

        Trade trade = tradeRepository.findById(id).get();

        TradeResponseDto tradeResponseDto = TradeResponseDto.builder()
                .id(trade.getId())
                .imgURL(trade.getImgURL())
                .expiryDate(trade.getExpiryDate())
                .state(trade.getState())
                .title(trade.getTitle())
//                            .likeCnt(tradeLike.getLikeCnt)
//                            .like(trade)
                .build();

        return ResponseDto.success(tradeResponseDto);
    }

    //물물교환 상품게시
    @Transactional
    public ResponseDto<String> addTrade(UserDetailsImpl userDetails, TradeRequestDto tradeRequestDto){
//        System.out.println(tradeRequestDto.getExpiryDate());
        Trade trade = Trade.builder()
                .title(tradeRequestDto.getTitle())
                .imgURL(tradeRequestDto.getImgURL())
                .expiryDate(LocalDate.parse(tradeRequestDto.getExpiryDate()))
                .content(tradeRequestDto.getContent())
                .purchasePrice(tradeRequestDto.getPurchasePrice())
                .categoryDetail(tradeRequestDto.getCategoryDetail())
                .member(userDetails.getMember())
                .build();

//        System.out.println(trade.getExpiryDate());
        tradeRepository.save(trade);

        return ResponseDto.success("게시 완료");
    }

    //물물교환 상품 정보 수정
    @Transactional
    public ResponseDto<String> updateTrade(UserDetailsImpl userDetails, TradeRequestDto tradeRequestDto, Long id){
        Trade trade = tradeRepository.findById(id).get();
        checkOwner(trade, userDetails);
        trade.updateTrade(tradeRequestDto);

        tradeRepository.save(trade);

        return ResponseDto.success("수정 완료");
    }

    //물물교환 상품 삭제
    @Transactional
    public ResponseDto<String> deleteTrade(UserDetailsImpl userDetails, Long id){
        Trade trade = tradeRepository.findById(id).get();

        checkOwner(trade, userDetails);

        tradeRepository.delete(trade);

        return ResponseDto.success("삭제 완료");
    }

    //권한 확인
    private void checkOwner(Trade trade, UserDetailsImpl userDetails){
        if(!trade.checkOwnerByMemberId(userDetails)){
            throw new IllegalArgumentException("회원님이 게시한 상품이 아닙니다.");
        }
    }
}
