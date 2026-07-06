package com.example.toyshop.domain;

import lombok.Data;

@Data
public class Order {

    private Long id;
    private Long memberId;

    private String orderNo;

    private Integer totalPrice;
    private Integer deliveryFee;
    private Integer finalPrice;

    private Integer useMileage;

    private String orderName;
    private String orderPhone;

    private String receiverName;
    private String receiverPhone;

    private String address;
    private String deliveryMessage;

    private String depositorName;
    private String bankInfo;

    private String orderStatus;

    private String paymentMethod;
    private String paymentStatus;
    private String tossPaymentKey;

    private String createdAt;

    private String deliveryCompany;
    private String trackingNo;
}