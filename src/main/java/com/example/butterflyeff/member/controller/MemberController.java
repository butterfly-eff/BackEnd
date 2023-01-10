package com.example.butterflyeff.member.controller;


<<<<<<< HEAD:src/main/java/com/example/butterflyeff/member/MemberController.java
import com.example.butterflyeff.ResponseDto;
import com.example.butterflyeff.security.UserDetailsImpl;
=======
import com.example.butterflyeff.member.dto.response.ResponseDto;
import com.example.butterflyeff.member.service.MemberService;
import com.example.butterflyeff.member.dto.request.SigninRequestDto;
import com.example.butterflyeff.member.dto.request.SignupRequestDto;
>>>>>>> 1630131c67844de54d3ce926036b0ee6e2757741:src/main/java/com/example/butterflyeff/member/controller/MemberController.java
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    // 회원가입
    @PostMapping("/signup")
    public ResponseDto<String> signup(@RequestBody SignupRequestDto signupRequestDto){
        return memberService.singup(signupRequestDto);
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseDto<String> signin(@RequestBody SigninRequestDto signinRequestDto, HttpServletResponse httpServletResponse){
        return memberService.signin(signinRequestDto, httpServletResponse);
    }

    // 로그아웃
    @PostMapping("/signout")
    public ResponseDto<String> signout(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return memberService.signout(userDetails.getMember().getEmail());
    }

    // 닉네임 중복확인
    @PostMapping("/nickNameCheck")
    public ResponseDto<String> nickNameCheck(@RequestBody NickNameCheckRequestDto nickNameCheckRequestDto){
        return memberService.nickNameCheck(nickNameCheckRequestDto);
    }

}
