package com.example.butterflyeff.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyProfileResponseDto {

    private String nickName;
    private String cityName;
    private String guName;
    private String dongName;
}
