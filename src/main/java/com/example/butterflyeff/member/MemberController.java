package com.example.butterflyeff.member;


import com.example.butterflyeff.ResponseDto;
import lombok.RequiredArgsConstructor;
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
}
