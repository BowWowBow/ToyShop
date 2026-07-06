package com.example.toyshop.domain;

import lombok.Data;

@Data
public class Inquiry {

    private Long id;
    private String category;
    private String writer;
    private String email;
    private String mailReceive;
    private String title;
    private String content;
    private String answerStatus;
    private String createdAt;
    private Long memberId;
    private String answerContent;
    private String answeredAt;
}