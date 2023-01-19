package com.example.butterflyeff.like.model;


import com.example.butterflyeff.member.model.Member;
import com.example.butterflyeff.trade.model.Trade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Trade trade;

}
