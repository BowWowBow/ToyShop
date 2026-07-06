package com.example.toyshop.mapper;

import com.example.toyshop.domain.Wishlist;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WishlistMapper {

    void insertWishlist(@Param("memberId") Long memberId,
                        @Param("productId") Long productId);

    void deleteWishlist(@Param("memberId") Long memberId,
                        @Param("productId") Long productId);

    int countWishlist(@Param("memberId") Long memberId,
                      @Param("productId") Long productId);

    List<Wishlist> findByMemberId(Long memberId);
}