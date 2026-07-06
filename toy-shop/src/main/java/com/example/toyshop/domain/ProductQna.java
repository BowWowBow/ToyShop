package com.example.toyshop.domain;

import lombok.Data;

@Data
public class ProductQna {

    private Long id;
    private Long productId;

    // 상품번호 대신 화면에 보여줄 상품명
    private String productName;

    private String writer;
    private String title;
    private String content;
    private String answerStatus;
    private String createdAt;
    private Long memberId;

    private String answerContent;
    private String answeredAt;

}