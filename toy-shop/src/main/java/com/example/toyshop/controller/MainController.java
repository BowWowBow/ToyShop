package com.example.toyshop.controller;

import com.example.toyshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final ProductService productService;

    public MainController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("newList", productService.findNewProductLimit(20));

        model.addAttribute("metalList", productService.findByCategoryLimit("초합금", 20));
        model.addAttribute("plasticList", productService.findByCategoryLimit("프라모델", 20));
        model.addAttribute("figureList", productService.findByCategoryLimit("피규어", 20));
        model.addAttribute("carList", productService.findByCategoryLimit("레이싱카", 20));
        model.addAttribute("dollList", productService.findByCategoryLimit("인형", 20));
        model.addAttribute("gameList", productService.findByCategoryLimit("게임기", 20));
        model.addAttribute("etcList", productService.findByCategoryLimit("기타", 20));

        return "index";
    }
}