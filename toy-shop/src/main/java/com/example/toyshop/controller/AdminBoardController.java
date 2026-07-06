package com.example.toyshop.controller;

import com.example.toyshop.domain.ProductQna;
import com.example.toyshop.mapper.InquiryMapper;
import com.example.toyshop.mapper.ProductQnaMapper;
import com.example.toyshop.mapper.ReviewMapper;
import com.example.toyshop.service.ProductQnaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.toyshop.domain.Inquiry;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminBoardController {

    private final InquiryMapper inquiryMapper;
    private final ProductQnaMapper productQnaMapper;
    private final ReviewMapper reviewMapper;
    private final ProductQnaService productQnaService;

    public AdminBoardController(InquiryMapper inquiryMapper,
                                ProductQnaMapper productQnaMapper,
                                ReviewMapper reviewMapper,
                                ProductQnaService productQnaService) {
        this.inquiryMapper = inquiryMapper;
        this.productQnaMapper = productQnaMapper;
        this.reviewMapper = reviewMapper;
        this.productQnaService = productQnaService;
    }

    @GetMapping("/admin/board/inquiry")
    public String inquiryList(Model model) {
        model.addAttribute("inquiryList", inquiryMapper.findAll());
        model.addAttribute("boardType", "inquiry");
        return "admin/board/inquiry-list";
    }

    @GetMapping("/admin/board/qna")
    public String qnaList(Model model) {
        model.addAttribute("qnaList", productQnaMapper.findAll());
        model.addAttribute("boardType", "qna");
        return "admin/board/qna-list";
    }

    @GetMapping("/admin/board/review")
    public String reviewList(Model model) {
        model.addAttribute("reviewList", reviewMapper.findAll());
        model.addAttribute("boardType", "review");
        return "admin/board/review-list";
    }

    @PostMapping("/admin/board/qna/answer")
    public String answer(ProductQna qna) {
        productQnaService.updateAnswer(qna);
        return "redirect:/admin/board/qna";
    }

    @PostMapping("/admin/board/inquiry/answer")
    public String answerInquiry(Inquiry inquiry) {
        inquiryMapper.updateAnswer(inquiry);
        return "redirect:/admin/board/inquiry";
    }

    @GetMapping("/admin/board/qna/detail/{id}")
    public String adminQnaDetail(@PathVariable Long id, Model model) {
        ProductQna qna = productQnaService.findById(id);
        model.addAttribute("qna", qna);

        return "member/product-qna-detail";
    }

    @GetMapping("/admin/board/inquiry/detail/{id}")
    public String adminInquiryDetail(@PathVariable Long id, Model model) {
        Inquiry inquiry = inquiryMapper.findById(id);
        model.addAttribute("inquiry", inquiry);

        return "member/inquiry-detail";
    }

}