package com.example.toyshop.domain;

import lombok.Data;

@Data
public class OrderDetail {

    private Long id;
    private Long orderId;
    private Long productId;
    private String productName;
    private Integer price;
    private Integer qty;
    private Integer totalPrice;
    private String imageUrl;
    private String createdAt;
}