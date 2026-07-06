package com.example.toyshop.domain;

import lombok.Data;

@Data
public class Review {

    private Long id;
    private Long memberId;
    private Long productId;

    private Integer rating;

    private String title;
    private String content;

    private String createdAt;

    private String productName;
    private String itemName;
    private String itemImage;

    private String loginId;
    private String memberName;

    private String writer;
}