package com.example.toyshop.controller;

import com.example.toyshop.domain.Inquiry;
import com.example.toyshop.domain.Member;
import com.example.toyshop.domain.ProductQna;
import com.example.toyshop.domain.Review;
import com.example.toyshop.service.InquiryService;
import com.example.toyshop.service.MemberService;
import com.example.toyshop.service.ProductQnaService;
import com.example.toyshop.service.ProductService;
import com.example.toyshop.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.toyshop.service.OrderService;

@Controller
public class MyPageController {

    private final InquiryService inquiryService;
    private final ProductQnaService productQnaService;
    private final ProductService productService;
    private final ReviewService reviewService;
    private final MemberService memberService;
    private final OrderService orderService;

    public MyPageController(InquiryService inquiryService,
                            ProductQnaService productQnaService,
                            ProductService productService,
                            ReviewService reviewService,
                            MemberService memberService,
                            OrderService orderService) {
        this.inquiryService = inquiryService;
        this.productQnaService = productQnaService;
        this.productService = productService;
        this.reviewService = reviewService;
        this.memberService = memberService;
        this.orderService = orderService;
    }

    @GetMapping("/member/inquiry")
    public String inquiry(HttpSession session, Model model) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        model.addAttribute(
                "inquiryList",
                inquiryService.findByMemberId(loginMember.getId())
        );

        return "member/inquiry";
    }

    @GetMapping("/member/inquiry/write")
    public String inquiryWrite(HttpSession session) {

        if (session.getAttribute("loginMember") == null) {
            return "redirect:/member/login";
        }

        return "member/inquiry-write";
    }

    @PostMapping("/member/inquiry/save")
    public String saveInquiry(Inquiry inquiry,
                              HttpSession session) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        inquiry.setMemberId(loginMember.getId());
        inquiry.setWriter(loginMember.getName());
        inquiry.setEmail(loginMember.getEmail());

        inquiryService.saveInquiry(inquiry);

        return "redirect:/member/inquiry";
    }

    @GetMapping("/member/inquiry/detail/{id}")
    public String inquiryDetail(@PathVariable Long id,
                                HttpSession session,
                                Model model) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        Inquiry inquiry = inquiryService.findById(id);

        if (inquiry == null ||
                !inquiry.getMemberId().equals(loginMember.getId())) {
            return "redirect:/member/inquiry";
        }

        model.addAttribute("inquiry", inquiry);

        return "member/inquiry-detail";
    }

    @GetMapping("/member/product-qna")
    public String productQna(HttpSession session, Model model) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        model.addAttribute(
                "productQnaList",
                productQnaService.findByMemberId(loginMember.getId())
        );

        return "member/product-qna";
    }

    @GetMapping("/member/product-qna/write")
    public String productQnaWrite(HttpSession session) {

        if (session.getAttribute("loginMember") == null) {
            return "redirect:/member/login";
        }

        return "member/product-qna-write";
    }

    @PostMapping("/member/product-qna/save")
    public String saveProductQna(ProductQna productQna,
                                 HttpSession session) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        productQna.setMemberId(loginMember.getId());
        productQna.setWriter(loginMember.getName());

        productQnaService.saveProductQna(productQna);

        return "redirect:/member/product-qna";
    }

    @GetMapping("/member/product-qna/detail/{id}")
    public String productQnaDetail(@PathVariable Long id,
                                   HttpSession session,
                                   Model model) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        ProductQna qna = productQnaService.findById(id);

        if (qna == null ||
                !qna.getMemberId().equals(loginMember.getId())) {
            return "redirect:/member/product-qna";
        }

        model.addAttribute("qna", qna);

        return "member/product-qna-detail";
    }

    @GetMapping("/member/review")
    public String review(HttpSession session,
                         Model model) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        model.addAttribute(
                "reviewList",
                reviewService.findByMemberId(loginMember.getId())
        );

        return "member/review";
    }

    @GetMapping("/member/review/write")
    public String reviewWriteForm(HttpSession session,
                                  Model model) {

        if (session.getAttribute("loginMember") == null) {
            return "redirect:/member/login";
        }

        model.addAttribute(
                "productList",
                orderService.findPurchasedProducts(
                        ((Member) session.getAttribute("loginMember")).getId()
                )
        );

        return "member/review-write";
    }

    @PostMapping("/member/review/write")
    public String saveReview(Review review,
                             HttpSession session) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        review.setMemberId(loginMember.getId());

        // ↓↓↓ 여기 한 줄 추가
        review.setWriter(loginMember.getName());

        reviewService.saveReview(review);

        return "redirect:/member/review";
    }

    @GetMapping("/member/edit")
    public String memberEdit(HttpSession session,
                             Model model) {

        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        Member member = memberService.findById(loginMember.getId());

        model.addAttribute("member", member);

        return "member/edit";
    }


}