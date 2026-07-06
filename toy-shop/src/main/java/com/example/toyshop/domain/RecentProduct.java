package com.example.toyshop.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RecentProduct {

    private Long id;

    private Long memberId;      // 회원번호

    private Long productId;     // 상품번호

    private String productName;

    private Integer price;

    private String imageUrl;

    private String category;

    private LocalDateTime viewedAt; // 본 시간
}