package com.example.butterflyeff.member.service;

import com.example.butterflyeff.member.NickNameCheckRequestDto;
import com.example.butterflyeff.member.dto.response.ResponseDto;
import com.example.butterflyeff.jwt.JwtUtil;
import com.example.butterflyeff.jwt.RefreshToken;
import com.example.butterflyeff.jwt.RefreshTokenRepository;
import com.example.butterflyeff.jwt.TokenDto;
import com.example.butterflyeff.member.dto.request.SigninRequestDto;
import com.example.butterflyeff.member.dto.request.SignupRequestDto;
import com.example.butterflyeff.member.model.Member;
import com.example.butterflyeff.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;


    // 회원가입
    public ResponseDto<String> singup(SignupRequestDto signupRequestDto){

        if(memberRepository.findByEmail(signupRequestDto.getEmail()).isPresent()){
            throw new RuntimeException("Overlap Check");
        }

        Member member = Member.builder()
                .email(signupRequestDto.getEmail())
                .nickName(signupRequestDto.getNickName())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .cityName(signupRequestDto.getCityName())
                .guName(signupRequestDto.getGuName())
                .dongName(signupRequestDto.getDongName())
                .build();

        memberRepository.save(member);

        return ResponseDto.success("회원가입 성공");
    }

    // 로그인
    public ResponseDto<String> signin(SigninRequestDto signinRequestDto, HttpServletResponse httpServletResponse){
        // userId 로 user 정보 호출
        Member member = memberRepository.findByEmail(signinRequestDto.getEmail()).orElseThrow(
                () -> new RuntimeException("Not found Account")
        );

        if (!passwordEncoder.matches(signinRequestDto.getPassword(), member.getPassword())) {
            return ResponseDto.fail(400, "비밀번호가 일치하지 않습니다", "Bad Request");
        }

        // userId 값을 포함한 토큰 생성 후 tokenDto 에 저장
        TokenDto tokenDto = jwtUtil.createAllToken(signinRequestDto.getEmail());

        // userId 값에 해당하는 refreshToken 을 DB에서 가져옴
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByEmail(signinRequestDto.getEmail());

        if (refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        } else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), signinRequestDto.getEmail());
            refreshTokenRepository.save(newToken);
        }

        setHeader(httpServletResponse, tokenDto);

        return ResponseDto.success(
                "로그인 성공"
        );
    }

    // 로그아웃
    public ResponseDto<String> signout(String email){
        // 해당 유저의 refreshtoken 이 없을 경우
        if (refreshTokenRepository.findByEmail(email).isEmpty()){
            return ResponseDto.fail(404,"Bad Request", "로그인을 해주세요.");
        }
        // 자신의 refreshtoken 만 삭제 가능
        String emailrepo = refreshTokenRepository.findByEmail(email).get().getEmail();
        if(email.equals(emailrepo)){
            refreshTokenRepository.deleteByEmail(email);
            return ResponseDto.success("로그아웃 성공");
        }else{
            return ResponseDto.fail(401,"Unauthorized","refreshtoken 삭제 권한이 없습니다.");
        }
    }

    // 닉네임 중복확인
    public ResponseDto<String> nickNameCheck(NickNameCheckRequestDto nickNameCheckRequestDto){
        String nickName = nickNameCheckRequestDto.getNickName();
        if(memberRepository.findByNickName(nickName).isPresent()){
            return ResponseDto.fail(400,"이미 등록된 닉네임입니다.", "Bad Request");
        }else{
            return ResponseDto.success("사용 가능한 닉네임입니다.");
        }
    }

    
    private void setHeader(HttpServletResponse httpServletResponse, TokenDto tokenDto) {
        httpServletResponse.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        httpServletResponse.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }
}
