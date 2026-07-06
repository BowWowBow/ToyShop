package com.example.toyshop.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MileageHistory {

    private Long id;

    private Long memberId;

    private Integer amount;

    private String type;

    private String description;

    private LocalDateTime createdAt;
}