package com.example.toyshop.domain;

import lombok.Data;

@Data
public class Cart {

    private Long memberId;

    private Long id;
    private Long productId;
    private Integer qty;
    private String createdAt;

    private String productName;
    private String imageUrl;
    private Integer price;
    private Integer stockQty;
}