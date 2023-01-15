package com.example.butterflyeff.trade.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TradeRequestDto {

    private String title;

    private String imgURL;

    private String expiryDate;

    private String content;

    private Long purchasePrice;

    private String categoryDetail;

}
