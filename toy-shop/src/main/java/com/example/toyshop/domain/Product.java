package com.example.toyshop.domain;

import lombok.Data;

@Data
public class Product {

    private Long id;

    private String productName;

    private String category;

    private Integer price;

    private Integer stockQty;

    private String imageUrl;

    private String description;

    private String createdAt;
}