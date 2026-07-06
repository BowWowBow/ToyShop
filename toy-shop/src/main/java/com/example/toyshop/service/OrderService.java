package com.example.toyshop.service;

import com.example.toyshop.domain.Cart;
import com.example.toyshop.domain.Order;
import com.example.toyshop.domain.OrderDetail;
import com.example.toyshop.domain.Product;
import com.example.toyshop.mapper.CartMapper;
import com.example.toyshop.mapper.OrderMapper;
import com.example.toyshop.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrderService {

    private final OrderMapper orderMapper;
    private final CartMapper cartMapper;
    private final ProductMapper productMapper;
    private final MileageService mileageService;

    public OrderService(OrderMapper orderMapper,
                        CartMapper cartMapper,
                        ProductMapper productMapper,
                        MileageService mileageService) {
        this.orderMapper = orderMapper;
        this.cartMapper = cartMapper;
        this.productMapper = productMapper;
        this.mileageService = mileageService;
    }

    @Transactional
    public Long orderAll(Order order, Long memberId) {

        List<Cart> cartList = cartMapper.findAllByMemberId(memberId);

        if (cartList.isEmpty()) {
            return null;
        }

        int totalPrice = 0;

        for (Cart cart : cartList) {
            totalPrice += cart.getPrice() * cart.getQty();
        }

        int deliveryFee = 3000;
        int originFinalPrice = totalPrice + deliveryFee;

        int useMileage = order.getUseMileage() == null ? 0 : order.getUseMileage();

        System.out.println("사용 마일리지 = " + useMileage);

        if (useMileage < 0) {
            useMileage = 0;
        }

        if (useMileage > originFinalPrice) {
            useMileage = originFinalPrice;
        }

        int finalPrice = originFinalPrice - useMileage;

        String orderNo = "ORD" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        order.setMemberId(memberId);
        order.setOrderNo(orderNo);
        order.setTotalPrice(totalPrice);
        order.setDeliveryFee(deliveryFee);
        order.setFinalPrice(finalPrice);

        if (order.getPaymentMethod() == null || order.getPaymentMethod().isBlank()) {
            order.setPaymentMethod("BANK");
        }

        if ("CARD".equals(order.getPaymentMethod())) {
            order.setOrderStatus("PAID");
            order.setPaymentStatus("PAID");
        } else {
            order.setOrderStatus("ORDERED");

            if (order.getPaymentStatus() == null || order.getPaymentStatus().isBlank()) {
                order.setPaymentStatus("WAITING");
            }
        }

        if ("BANK".equals(order.getPaymentMethod())) {
            order.setTossPaymentKey(null);
        }

        orderMapper.insertOrder(order);

        if (useMileage > 0) {
            mileageService.useMileage(memberId, useMileage);
        }

        for (Cart cart : cartList) {

            OrderDetail detail = new OrderDetail();

            detail.setOrderId(order.getId());
            detail.setProductId(cart.getProductId());
            detail.setProductName(cart.getProductName());
            detail.setPrice(cart.getPrice());
            detail.setQty(cart.getQty());
            detail.setTotalPrice(cart.getPrice() * cart.getQty());
            detail.setImageUrl(cart.getImageUrl());

            orderMapper.insertOrderDetail(detail);

            int decreaseResult =
                    productMapper.decreaseStock(cart.getProductId(), cart.getQty());

            if (decreaseResult == 0) {
                throw new RuntimeException("재고가 부족하거나 상품을 찾을 수 없습니다.");
            }

            cartMapper.deleteCart(cart.getId(), memberId);
        }

        return order.getId();
    }

    public Order findById(Long id) {
        return orderMapper.findById(id);
    }

    public List<Order> findAllOrders() {
        return orderMapper.findAllOrders();
    }

    public List<Order> findAdminOrderList(String startDate,
                                          String endDate,
                                          String sort,
                                          String dir) {
        return orderMapper.findAdminOrderList(startDate, endDate, sort, dir);
    }

    public List<Order> findByMemberId(Long memberId) {
        return orderMapper.findByMemberId(memberId);
    }

    @Transactional
    public void approveBankOrder(Long id) {
        orderMapper.approveBankOrder(id);
    }

    @Transactional
    public void updateOrderStatus(Long id, String orderStatus) {
        orderMapper.updateOrderStatus(id, orderStatus);
    }

    @Transactional
    public void doneOrder(Long id) {

        Order order = orderMapper.findById(id);

        if (order == null) {
            return;
        }

        if ("DONE".equals(order.getOrderStatus())) {
            return;
        }

        orderMapper.updateOrderStatus(id, "DONE");

        mileageService.orderMileage(order.getMemberId(), order.getTotalPrice());
    }

    @Transactional
    public void rollbackOrder(Long id) {

        Order order = orderMapper.findById(id);

        if (order == null) {
            return;
        }

        switch (order.getOrderStatus()) {

            case "DONE":
                orderMapper.updateOrderStatus(id, "DELIVERY");
                mileageService.cancelOrderMileage(order.getMemberId(), order.getTotalPrice());
                break;

            case "DELIVERY":
                orderMapper.updateOrderStatus(id, "READY");
                orderMapper.clearDeliveryInfo(id);
                break;

            case "READY":
                orderMapper.updateOrderStatus(id, "PAID");
                orderMapper.clearDeliveryInfo(id);
                break;

            case "PAID":
                if ("BANK".equals(order.getPaymentMethod())) {
                    orderMapper.rollbackBankPayment(id);
                }
                orderMapper.clearDeliveryInfo(id);
                break;
        }
    }

    @Transactional
    public void updateDeliveryInfo(Long id, String deliveryCompany, String trackingNo) {
        orderMapper.updateDeliveryInfo(id, deliveryCompany, trackingNo);
        orderMapper.updateOrderStatus(id, "DELIVERY");
    }

    /*
     * 회원 직접 주문취소
     * 가능 상태: ORDERED(입금대기), PAID(결제완료), READY(상품준비)
     * 불가 상태: DELIVERY(배송중), DONE(배송완료), CANCEL(이미취소)
     *
     * 여기서는 DB 기준 취소 처리까지 합니다.
     * 카드결제 Toss 실제 승인취소 API 호출은 다음 단계에서 Controller/Service에 연결합니다.
     */
    @Transactional
    public void cancelOrderByMember(Long orderId, Long memberId) {

        Order order = orderMapper.findById(orderId);

        if (order == null) {
            throw new IllegalArgumentException("주문 정보를 찾을 수 없습니다.");
        }

        if (!order.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("본인 주문만 취소할 수 있습니다.");
        }

        if ("DELIVERY".equals(order.getOrderStatus()) ||
                "DONE".equals(order.getOrderStatus()) ||
                "CANCEL".equals(order.getOrderStatus())) {
            throw new IllegalStateException("현재 상태에서는 주문을 취소할 수 없습니다.");
        }

        List<OrderDetail> orderItemList = orderMapper.findOrderItems(orderId);

        for (OrderDetail item : orderItemList) {
            productMapper.increaseStock(item.getProductId(), item.getQty());
        }

        int usedMileage = 0;

        if (order.getTotalPrice() != null &&
                order.getDeliveryFee() != null &&
                order.getFinalPrice() != null) {

            usedMileage = order.getTotalPrice()
                    + order.getDeliveryFee()
                    - order.getFinalPrice();
        }

        if (usedMileage > 0) {
            mileageService.restoreUsedMileage(order.getMemberId(), usedMileage);
        }

        orderMapper.cancelOrder(orderId);
    }

    public List<OrderDetail> findOrderItems(Long orderId) {
        return orderMapper.findOrderItems(orderId);
    }

    public List<Order> findByMemberIdAndDate(Long memberId, String startDate, String endDate) {
        return orderMapper.findByMemberIdAndDate(memberId, startDate, endDate);
    }

    public List<Product> findPurchasedProducts(Long memberId) {
        return orderMapper.findPurchasedProducts(memberId);
    }
}
