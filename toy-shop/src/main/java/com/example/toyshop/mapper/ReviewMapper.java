package com.example.toyshop.mapper;

import com.example.toyshop.domain.Review;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewMapper {

    void insertReview(Review review);

    List<Review> findAll();

    List<Review> findByMemberId(@Param("memberId") Long memberId);

    List<Review> findByProductId(Long productId);
}