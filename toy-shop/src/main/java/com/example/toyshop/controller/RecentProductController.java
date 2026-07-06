package com.example.toyshop.controller;

import com.example.toyshop.domain.Member;
import com.example.toyshop.service.RecentProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recent")
public class RecentProductController {

    private final RecentProductService recentProductService;

    public RecentProductController(RecentProductService recentProductService) {
        this.recentProductService = recentProductService;
    }

    @GetMapping("/list")
    public String recentList(HttpSession session, Model model) {

        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        Long memberId = loginMember.getId();

        model.addAttribute(
                "recentList",
                recentProductService.getRecentProductList(memberId)
        );

        return "recent/list";
    }

    @PostMapping("/delete")
    @ResponseBody
    public String deleteRecent(@RequestParam Long productId,
                               HttpSession session) {

        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "loginRequired";
        }

        Long memberId = loginMember.getId();

        recentProductService.deleteRecentProduct(memberId, productId);

        return "success";
    }
}