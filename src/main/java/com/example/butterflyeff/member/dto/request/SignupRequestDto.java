package com.example.butterflyeff.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupRequestDto {

    private String email;
    private String nickName;
    private String password;
    private String passwordCheck;
    private String cityName;
    private String guName;
    private String dongName;
}
