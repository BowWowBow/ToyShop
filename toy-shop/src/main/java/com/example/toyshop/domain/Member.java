package com.example.toyshop.domain;

import lombok.Data;

@Data
public class Member {

    private Long id;

    private String loginId;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String address;

    private String role;
    private String createdAt;

    private String zipcode;
    private String addressDetail;
    private String birth;
    private String gender;

    private String status;

    private Integer mileage;
    private Integer totalOrderAmount;
}