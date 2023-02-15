package com.example.butterflyeff.mypage.controller;

import com.example.butterflyeff.like.dto.LikeResponseDto;
import com.example.butterflyeff.member.dto.response.ResponseDto;
import com.example.butterflyeff.mypage.dto.MyProfileResponseDto;
import com.example.butterflyeff.mypage.service.MyPageService;
import com.example.butterflyeff.security.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    //마이페이지 프로필 조회
    @GetMapping("/mypage/myprofile")
    public ResponseDto<MyProfileResponseDto> getMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return myPageService.getMyProfile(userDetails);
    }

    //마이페이지 상품조회
    @GetMapping("/mypage/like")
    public ResponseDto<List<LikeResponseDto>> getMyTrade(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return myPageService.getMyTrade(userDetails);
    }
}
