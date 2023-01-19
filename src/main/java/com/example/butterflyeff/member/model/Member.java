package com.example.butterflyeff.member.model;


import com.example.butterflyeff.like.model.Like;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "members")
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    private String email;

    private String nickName;

    private String password;

    private String cityName;

    private String guName;

    private String dongName;

    @OneToMany(mappedBy = "like", cascade = CascadeType.ALL)
    private List<Like> like = new ArrayList<>();
}
