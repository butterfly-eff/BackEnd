package com.example.butterflyeff.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SigninRequestDto {

    private String email;
    private String password;
}
