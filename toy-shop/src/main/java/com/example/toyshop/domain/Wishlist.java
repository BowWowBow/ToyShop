package com.example.toyshop.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Wishlist {

    private Long id;

    private Long memberId;

    private Long productId;

    private LocalDateTime createdAt;

    private String productName;

    private Integer price;

    private String imageUrl;

    private String category;
}