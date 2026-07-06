package com.example.toyshop.service;

import com.example.toyshop.domain.Cart;
import com.example.toyshop.mapper.CartMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private final CartMapper cartMapper;

    public CartService(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    @Transactional
    public void addCart(Cart cart) {

        Integer stockQty = cartMapper.findStockQtyByProductId(cart.getProductId());

        if (stockQty == null || stockQty <= 0) {
            return;
        }

        Cart savedCart = cartMapper.findByProductIdAndMemberId(
                cart.getProductId(),
                cart.getMemberId()
        );

        if (savedCart == null) {
            if (cart.getQty() > stockQty) {
                cart.setQty(stockQty);
            }

            cartMapper.insertCart(cart);
        } else {
            int newQty = savedCart.getQty() + cart.getQty();

            if (newQty > stockQty) {
                newQty = stockQty;
            }

            cartMapper.updateQty(savedCart.getId(), newQty);
        }
    }

    public List<Cart> findAllByMemberId(Long memberId) {
        return cartMapper.findAllByMemberId(memberId);
    }

    public void updateQty(Long id, Integer qty) {

        Cart cart = cartMapper.findById(id);

        if (cart == null) {
            return;
        }

        Integer stockQty = cartMapper.findStockQtyByProductId(cart.getProductId());

        if (qty < 1) {
            qty = 1;
        }

        if (stockQty != null && qty > stockQty) {
            qty = stockQty;
        }

        cartMapper.updateQty(id, qty);
    }

    public void deleteCart(Long id, Long memberId) {
        cartMapper.deleteCart(id, memberId);
    }

    public void deleteAllByMemberId(Long memberId) {
        cartMapper.deleteAllByMemberId(memberId);
    }
}