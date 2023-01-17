package com.example.butterflyeff.like.controller;


import com.example.butterflyeff.like.service.LikeService;
import com.example.butterflyeff.member.dto.response.ResponseDto;
import com.example.butterflyeff.security.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    // 좋아요 추가
    @PostMapping("/product/trade/{id}/like")
    public ResponseDto<String> addLike(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                       @PathVariable Long id){
        return likeService.addLike(userDetails, id);
    }
}
