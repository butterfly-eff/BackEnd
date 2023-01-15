package com.example.butterflyeff.member.service;

import com.example.butterflyeff.member.dto.request.SignupRequestDto;
import com.example.butterflyeff.member.model.Member;
import com.example.butterflyeff.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    MemberService memberService;
    MemberRepository memberRepository;

    @Test
    void singup() {
        //given
//        Member member = Member.builder()
//                .email("adbcdef@gmail.com")
//                .nickName("장경원")
//                .password("abcdefg")
//                .cityName("서울시")
//                .guName("양천구")
//                .dongName("양천동")
//                .build();

                SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .email("adbcdef@gmail.com")
                .nickName("장경원")
                .password("abcdefg")
                .cityName("서울시")
                .guName("양천구")
                .dongName("양천동")
                .build();


//        System.out.println(member.getNickName());
        //when
        memberService.singup(signupRequestDto);
//        memberRepository.save(member);
        Member findMember = memberRepository.findByNickName("장경원").get();
//        Member findMember1 = memberRepository.findByEmail("adbcdef@gmail.com").get();

        //then
        Assertions.assertThat(signupRequestDto.getNickName()).isEqualTo(findMember.getNickName());
    }

    @Test
    void signin() {
    }

    @Test
    void signout() {
    }

    @Test
    void nickNameCheck() {
    }
}