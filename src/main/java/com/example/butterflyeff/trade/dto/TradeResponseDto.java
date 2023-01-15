package com.example.butterflyeff.trade.dto;

import com.example.butterflyeff.trade.model.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeResponseDto {

    private Long id;
    private String title;
    private String imgURL;
    private State state;
    private int likeCnt;
    private int like;
    private LocalDate expiryDate;
}
