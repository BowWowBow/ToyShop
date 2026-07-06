package com.example.toyshop.service;

import com.example.toyshop.domain.Wishlist;
import com.example.toyshop.mapper.WishlistMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistMapper wishlistMapper;

    public WishlistService(WishlistMapper wishlistMapper) {
        this.wishlistMapper = wishlistMapper;
    }

    public List<Wishlist> findByMemberId(Long memberId) {
        return wishlistMapper.findByMemberId(memberId);
    }

    public boolean isWish(Long memberId, Long productId) {
        return wishlistMapper.countWishlist(memberId, productId) > 0;
    }

    @Transactional
    public boolean toggleWishlist(Long memberId, Long productId) {

        int count = wishlistMapper.countWishlist(memberId, productId);

        if (count > 0) {
            wishlistMapper.deleteWishlist(memberId, productId);
            return false;
        }

        wishlistMapper.insertWishlist(memberId, productId);
        return true;
    }

    @Transactional
    public void deleteWishlist(Long memberId, Long productId) {
        wishlistMapper.deleteWishlist(memberId, productId);
    }
}