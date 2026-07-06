package com.example.toyshop.service;

import com.example.toyshop.domain.RecentProduct;
import com.example.toyshop.mapper.RecentProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecentProductService {

    private final RecentProductMapper recentProductMapper;

    public RecentProductService(RecentProductMapper recentProductMapper) {
        this.recentProductMapper = recentProductMapper;
    }

    // 최근 본 상품 저장
    @Transactional
    public void addRecentProduct(Long memberId, Long productId) {

        if (memberId == null || productId == null) {
            return;
        }

        RecentProduct recentProduct = new RecentProduct();
        recentProduct.setMemberId(memberId);
        recentProduct.setProductId(productId);

        // 같은 상품이 있으면 삭제
        recentProductMapper.deleteRecent(recentProduct);

        // 다시 저장 → 최신순으로 올라감
        recentProductMapper.insertRecent(recentProduct);
    }

    // 최근 본 상품 목록 조회
    public List<RecentProduct> getRecentProductList(Long memberId) {

        if (memberId == null) {
            return List.of();
        }

        return recentProductMapper.findRecentList(memberId);
    }

    // 최근 본 상품 1개 삭제
    @Transactional
    public void deleteRecentProduct(Long memberId, Long productId) {

        if (memberId == null || productId == null) {
            return;
        }

        RecentProduct recentProduct = new RecentProduct();
        recentProduct.setMemberId(memberId);
        recentProduct.setProductId(productId);

        recentProductMapper.deleteRecent(recentProduct);
    }
}