package com.example.toyshop.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Address {

    private Long id;

    private Long memberId;

    private String receiverName;

    private String receiverPhone;

    private String zipcode;

    private String address;

    private String addressDetail;

    private String isDefault;

    private String defaultYn;

    private LocalDateTime createdAt;
}