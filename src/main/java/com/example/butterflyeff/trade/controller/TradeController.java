package com.example.butterflyeff.trade.controller;

import com.example.butterflyeff.member.dto.response.ResponseDto;
import com.example.butterflyeff.security.auth.UserDetailsImpl;
import com.example.butterflyeff.trade.dto.TradeRequestDto;
import com.example.butterflyeff.trade.dto.TradeResponseDto;
import com.example.butterflyeff.trade.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class TradeController {
    private final TradeService tradeService;

    //물물교환 전체조회
    @GetMapping("/product/trade")
    public ResponseDto<List<TradeResponseDto>> getAllTrade(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return tradeService.getAllTrade(userDetails);
    }

    //물물교환 상품 상세조회
    @GetMapping("product/trade/{id}")
    public ResponseDto<TradeResponseDto> getTrade(@PathVariable Long id){
        return tradeService.getTrade(id);
    }

    //물물교환 상품게시
    @PostMapping("/product/trade")
    public ResponseDto<String> addTrade(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                        @RequestBody TradeRequestDto tradeRequestDto){
        return tradeService.addTrade( userDetails, tradeRequestDto);
    }

    //물물교환 상품정보수정
    @PutMapping("/product/trade/{id}")
    public ResponseDto<String> updateTrade(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @RequestBody TradeRequestDto tradeRequestDto,
                                           @PathVariable Long id){
        return tradeService.updateTrade(userDetails,tradeRequestDto, id);
    }

    //물물교환 상품 삭제
    @DeleteMapping("/product/trade/{id}")
    public ResponseDto<String> deleteTrade(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @PathVariable Long id){
        return tradeService.deleteTrade(userDetails,id);
    }
}
