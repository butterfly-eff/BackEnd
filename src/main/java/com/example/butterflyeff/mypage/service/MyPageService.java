package com.example.butterflyeff.mypage.service;

import com.example.butterflyeff.like.dto.LikeResponseDto;
import com.example.butterflyeff.like.model.Like;
import com.example.butterflyeff.like.repository.LikeRepository;
import com.example.butterflyeff.member.dto.response.ResponseDto;
import com.example.butterflyeff.member.model.Member;
import com.example.butterflyeff.member.repository.MemberRepository;
import com.example.butterflyeff.mypage.dto.MyProfileResponseDto;
import com.example.butterflyeff.security.auth.UserDetailsImpl;
import com.example.butterflyeff.trade.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

    private final MemberRepository memberRepository;
    private final TradeRepository tradeRepository;
    private final LikeRepository likeRepository;

    //마이페이지 프로필 조회
    public ResponseDto<MyProfileResponseDto> getMyProfile(UserDetailsImpl userDetails) {
        Member member = memberRepository.findByEmail(userDetails.getMember().getEmail()).get();
        MyProfileResponseDto myProfileResponseDto = MyProfileResponseDto.builder()
                    .nickName(member.getNickName())
                    .cityName(member.getCityName())
                    .guName(member.getGuName())
                    .dongName(member.getDongName())
                    .build();

        return ResponseDto.success(myProfileResponseDto);
    }

    //마이페이지 상품조회
    public ResponseDto<List<LikeResponseDto>> getMyTrade(UserDetailsImpl userDetails){
        Member member = memberRepository.findByEmail(userDetails.getMember().getEmail()).get();
        List<Like> likes = likeRepository.findAllByMemberId(member.getId());
        List<LikeResponseDto> likeResponseDtoList = new ArrayList<>();

        for (Like like : likes) {
            likeResponseDtoList.add(
                    LikeResponseDto.builder()
                            .id(like.getTrade().getId())
                            .title(like.getTrade().getTitle())
                            .imgURL(like.getTrade().getImgURL())
                            .build()
            );
        }

        return ResponseDto.success(likeResponseDtoList);
    }
}
