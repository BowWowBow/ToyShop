package com.example.toyshop.mapper;

import com.example.toyshop.domain.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartMapper {

    void insertCart(Cart cart);

    Cart findById(Long id);

    Cart findByProductIdAndMemberId(@Param("productId") Long productId,
                                    @Param("memberId") Long memberId);

    Integer findStockQtyByProductId(Long productId);

    void increaseQty(@Param("productId") Long productId,
                     @Param("memberId") Long memberId,
                     @Param("qty") Integer qty);

    void updateQty(@Param("id") Long id,
                   @Param("qty") Integer qty);

    List<Cart> findAllByMemberId(Long memberId);

    void deleteCart(@Param("id") Long id,
                    @Param("memberId") Long memberId);

    void deleteAllByMemberId(Long memberId);
}