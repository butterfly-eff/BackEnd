package com.example.butterflyeff.trade.model;

import com.example.butterflyeff.like.model.Like;
import com.example.butterflyeff.member.model.Member;
import com.example.butterflyeff.security.auth.UserDetailsImpl;
import com.example.butterflyeff.trade.dto.TradeRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_id", nullable = false)
    private Long id;

    private String title;

    private String imgURL;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;

    private String content;

    private Long purchasePrice;

    private String categoryDetail;

    private Long likeCnt;

    @OneToMany(mappedBy = "trade", cascade = CascadeType.ALL)
    private List<Like> like = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private State state;

    public boolean checkOwnerByMemberId(UserDetailsImpl userDetails){
        return this.member.getEmail().equals(userDetails.getMember().getEmail());
    }

    public void updateTrade(TradeRequestDto tradeRequestDto){
        this.title = tradeRequestDto.getTitle();
        this.imgURL = tradeRequestDto.getImgURL();
        this.expiryDate = LocalDate.parse(tradeRequestDto.getExpiryDate());
        this.content = tradeRequestDto.getContent();
        this.purchasePrice = tradeRequestDto.getPurchasePrice();
        this.categoryDetail = tradeRequestDto.getCategoryDetail();
    }
}
