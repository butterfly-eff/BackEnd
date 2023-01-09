package com.example.butterflyeff.member;

import com.example.butterflyeff.ResponseDto;
import com.example.butterflyeff.jwt.JwtUtil;
import com.example.butterflyeff.jwt.RefreshToken;
import com.example.butterflyeff.jwt.RefreshTokenRepository;
import com.example.butterflyeff.jwt.TokenDto;
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
            return ResponseDto.fail(500, "비밀번호가 일치하지 않습니다", "Bad Request");
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

    private void setHeader(HttpServletResponse httpServletResponse, TokenDto tokenDto) {
        httpServletResponse.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        httpServletResponse.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }
}
