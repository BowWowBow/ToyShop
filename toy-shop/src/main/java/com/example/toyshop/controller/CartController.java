package com.example.toyshop.controller;

import com.example.toyshop.domain.Cart;
import com.example.toyshop.domain.Member;
import com.example.toyshop.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String cartList(Model model, HttpSession session) {

        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        List<Cart> cartList = cartService.findAllByMemberId(loginMember.getId());

        int totalPrice = 0;

        for (Cart cart : cartList) {
            totalPrice += cart.getPrice() * cart.getQty();
        }

        int deliveryFee = cartList.isEmpty() ? 0 : 3000;
        int finalPrice = totalPrice + deliveryFee;

        model.addAttribute("cartList", cartList);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("deliveryFee", deliveryFee);
        model.addAttribute("finalPrice", finalPrice);

        return "cart/cart";
    }

    @PostMapping("/cart/add")
    public String addCart(@RequestParam Long productId,
                          @RequestParam Integer qty,
                          HttpSession session) {

        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        Cart cart = new Cart();
        cart.setMemberId(loginMember.getId());
        cart.setProductId(productId);
        cart.setQty(qty);

        cartService.addCart(cart);

        return "redirect:/cart";
    }

    @PostMapping("/cart/update")
    public String updateCartQty(@RequestParam Long id,
                                @RequestParam Integer qty) {

        cartService.updateQty(id, qty);

        return "redirect:/cart";
    }

    @PostMapping("/cart/delete")
    public String deleteCart(@RequestParam Long id,
                             HttpSession session) {

        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        cartService.deleteCart(id, loginMember.getId());

        return "redirect:/cart";
    }

    @PostMapping("/cart/buy-now")
    public String buyNow(@RequestParam Long productId,
                         @RequestParam Integer qty,
                         HttpSession session) {

        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        cartService.deleteAllByMemberId(loginMember.getId());

        Cart cart = new Cart();
        cart.setMemberId(loginMember.getId());
        cart.setProductId(productId);
        cart.setQty(qty);

        cartService.addCart(cart);

        return "redirect:/order/pay";
    }
}