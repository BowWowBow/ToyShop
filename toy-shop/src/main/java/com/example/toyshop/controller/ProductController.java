package com.example.toyshop.controller;

import com.example.toyshop.domain.Member;
import com.example.toyshop.domain.Product;
import com.example.toyshop.mapper.ReviewMapper;
import com.example.toyshop.service.ProductService;
import com.example.toyshop.service.RecentProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private final RecentProductService recentProductService;
    private final ReviewMapper reviewMapper;

    public ProductController(ProductService productService,
                             RecentProductService recentProductService,
                             ReviewMapper reviewMapper) {
        this.productService = productService;
        this.recentProductService = recentProductService;
        this.reviewMapper = reviewMapper;
    }

    @GetMapping("/product/list")
    public String list(@RequestParam(required = false) String category,
                       Model model) {

        List<Product> productList;

        if (category == null || category.isBlank() || category.equals("전체")) {
            productList = productService.findAll();
            category = "전체";
        } else {
            productList = productService.findByCategory(category);
        }

        model.addAttribute("productList", productList);
        model.addAttribute("category", category);

        return "product/list";
    }

    @GetMapping("/product/detail/{id}")
    public String detail(@PathVariable Long id,
                         Model model,
                         HttpSession session) {

        Product product = productService.findById(id);

        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember != null && product != null) {
            recentProductService.addRecentProduct(
                    loginMember.getId(),
                    product.getId()
            );
        }

        model.addAttribute("product", product);
        model.addAttribute("reviewList", reviewMapper.findByProductId(id));

        return "product/detail";
    }

    @GetMapping("/api/product/search")
    @ResponseBody
    public List<Product> searchProducts(@RequestParam String keyword) {
        return productService.searchProducts(keyword);
    }
}