package com.example.toyshop.controller;

import com.example.toyshop.domain.Product;
import com.example.toyshop.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    private boolean isNotAdmin(HttpSession session) {
        String loginRole = (String) session.getAttribute("loginRole");
        return loginRole == null || !loginRole.equals("ADMIN");
    }

    private String saveImageFile(MultipartFile imageFile) throws IOException {

        if (imageFile == null || imageFile.isEmpty()) {
            return null;
        }

        String uploadDir =
                System.getProperty("user.dir")
                        + "/src/main/resources/static/images/upload/";

        File dir = new File(uploadDir);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        String originalFileName = imageFile.getOriginalFilename();
        String ext = "";

        if (originalFileName != null && originalFileName.contains(".")) {
            ext = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        String savedFileName = UUID.randomUUID() + ext;

        File saveFile = new File(uploadDir + savedFileName);
        imageFile.transferTo(saveFile);

        return "/images/upload/" + savedFileName;
    }

    @GetMapping("/list")
    public String list(@RequestParam(required = false) String startDate,
                       @RequestParam(required = false) String endDate,
                       @RequestParam(required = false, defaultValue = "id") String sort,
                       @RequestParam(required = false, defaultValue = "desc") String dir,
                       HttpSession session,
                       Model model) {

        if (isNotAdmin(session)) {
            return "redirect:/member/login";
        }

        if (startDate == null) startDate = "";
        if (endDate == null) endDate = "";
        if (!dir.equals("asc") && !dir.equals("desc")) dir = "desc";

        List<Product> productList =
                productService.findAdminProductList(startDate, endDate, sort, dir);

        String nextDir = dir.equals("asc") ? "desc" : "asc";

        model.addAttribute("productList", productList);
        model.addAttribute("productCount", productList.size());
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        model.addAttribute("nextDir", nextDir);

        return "admin/product/list";
    }

    @GetMapping("/new")
    public String newForm(HttpSession session) {

        if (isNotAdmin(session)) {
            return "redirect:/member/login";
        }

        return "admin/product/new";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Product product,
                       @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                       HttpSession session) throws IOException {

        if (isNotAdmin(session)) {
            return "redirect:/member/login";
        }

        String imageUrl = saveImageFile(imageFile);

        if (imageUrl != null) {
            product.setImageUrl(imageUrl);
        } else {
            product.setImageUrl("/images/no-image.png");
        }

        productService.save(product);

        return "redirect:/admin/product/list";
    }

    @GetMapping("/edit-search")
    public String editSearchPage(HttpSession session) {

        if (isNotAdmin(session)) {
            return "redirect:/member/login";
        }

        return "admin/product/edit-search";
    }

    @ResponseBody
    @GetMapping("/search")
    public List<Product> searchProduct(@RequestParam String keyword,
                                       HttpSession session) {

        if (isNotAdmin(session)) {
            return List.of();
        }

        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }

        return productService.searchByName(keyword);
    }

    @ResponseBody
    @GetMapping("/api/{id}")
    public Product productApi(@PathVariable Long id,
                              HttpSession session) {

        if (isNotAdmin(session)) {
            return null;
        }

        return productService.findById(id);
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Product product,
                         @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                         HttpSession session,
                         Model model) throws IOException {

        if (isNotAdmin(session)) {
            return "redirect:/member/login";
        }

        Product oldProduct = productService.findById(product.getId());

        String imageUrl = saveImageFile(imageFile);

        if (imageUrl != null) {
            product.setImageUrl(imageUrl);
        } else {
            product.setImageUrl(oldProduct.getImageUrl());
        }

        productService.update(product);

        model.addAttribute("message", "수정되었습니다");
        model.addAttribute("product", productService.findById(product.getId()));

        return "admin/product/edit-search";
    }

    @GetMapping("/detail/{id}")
    public String adminProductDetail(@PathVariable Long id,
                                     HttpSession session,
                                     Model model) {

        if (isNotAdmin(session)) {
            return "redirect:/member/login";
        }

        Product product = productService.findById(id);
        model.addAttribute("product", product);

        return "admin/product/detail";
    }
}