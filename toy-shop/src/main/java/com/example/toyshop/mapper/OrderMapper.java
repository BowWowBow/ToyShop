package com.example.toyshop.mapper;

import com.example.toyshop.domain.Order;
import com.example.toyshop.domain.OrderDetail;
import com.example.toyshop.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {

    void insertOrder(Order order);

    void insertOrderDetail(OrderDetail orderDetail);

    Order findById(Long id);

    List<Order> findAllOrders();

    List<Order> findAdminOrderList(@Param("startDate") String startDate,
                                   @Param("endDate") String endDate,
                                   @Param("sort") String sort,
                                   @Param("dir") String dir);

    List<Order> findByMemberId(Long memberId);

    void approveBankOrder(Long id);

    void updateOrderStatus(@Param("id") Long id,
                           @Param("orderStatus") String orderStatus);

    void rollbackBankPayment(Long id);

    void updateDeliveryInfo(@Param("id") Long id,
                            @Param("deliveryCompany") String deliveryCompany,
                            @Param("trackingNo") String trackingNo);

    List<OrderDetail> findOrderItems(Long orderId);

    List<Order> findByMemberIdAndDate(@Param("memberId") Long memberId,
                                      @Param("startDate") String startDate,
                                      @Param("endDate") String endDate);

    void clearDeliveryInfo(Long id);

    List<Product> findPurchasedProducts(Long memberId);

    // 회원 주문취소 처리
    void cancelOrder(Long id);
}
