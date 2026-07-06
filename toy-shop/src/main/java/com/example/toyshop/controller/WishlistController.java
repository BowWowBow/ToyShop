package com.example.toyshop.controller;

import com.example.toyshop.domain.Member;
import com.example.toyshop.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    private Long getLoginMemberId(HttpSession session) {

        Long memberId = (Long) session.getAttribute("loginMemberId");

        if (memberId != null) {
            return memberId;
        }

        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember != null) {
            return loginMember.getId();
        }

        return null;
    }

    @PostMapping("/add")
    @ResponseBody
    public String addWishlist(@RequestParam Long itemId,
                              HttpSession session) {

        Long memberId = getLoginMemberId(session);

        if (memberId == null) {
            return "login";
        }

        boolean alreadyWish = wishlistService.isWish(memberId, itemId);

        if (!alreadyWish) {
            wishlistService.toggleWishlist(memberId, itemId);
        }

        return "success";
    }

    @PostMapping("/delete")
    @ResponseBody
    public String deleteWishlist(@RequestParam Long itemId,
                                 HttpSession session) {

        Long memberId = getLoginMemberId(session);

        if (memberId == null) {
            return "login";
        }

        wishlistService.deleteWishlist(memberId, itemId);

        return "success";
    }

    @PostMapping("/toggle")
    @ResponseBody
    public String toggleWishlist(@RequestParam Long itemId,
                                 HttpSession session) {

        Long memberId = getLoginMemberId(session);

        if (memberId == null) {
            return "login";
        }

        boolean added = wishlistService.toggleWishlist(memberId, itemId);

        return added ? "added" : "deleted";
    }

    @GetMapping("/list")
    public String wishlistPage(HttpSession session,
                               Model model) {

        Long memberId = getLoginMemberId(session);

        if (memberId == null) {
            return "redirect:/member/login";
        }

        model.addAttribute("wishlist", wishlistService.findByMemberId(memberId));

        return "wishlist/list";
    }
}