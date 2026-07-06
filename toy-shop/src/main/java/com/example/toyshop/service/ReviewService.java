package com.example.toyshop.service;

import com.example.toyshop.domain.Review;
import com.example.toyshop.mapper.ReviewMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewMapper reviewMapper;

    public ReviewService(ReviewMapper reviewMapper) {
        this.reviewMapper = reviewMapper;
    }

    public void saveReview(Review review) {
        reviewMapper.insertReview(review);
    }

    public List<Review> findAll() {
        return reviewMapper.findAll();
    }

    public List<Review> findByMemberId(Long memberId) {
        return reviewMapper.findByMemberId(memberId);
    }
}