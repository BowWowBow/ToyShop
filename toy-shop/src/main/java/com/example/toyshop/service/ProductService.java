package com.example.toyshop.service;

import com.example.toyshop.domain.Product;
import com.example.toyshop.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public List<Product> findAll() {
        return productMapper.findAll();
    }

    public List<Product> findByCategory(String category) {
        return productMapper.findByCategory(category);
    }

    public Product findById(Long id) {
        return productMapper.findById(id);
    }

    public List<Product> searchByName(String keyword) {
        return productMapper.searchByName(keyword);
    }

    @Transactional
    public void save(Product product) {
        productMapper.insertProduct(product);
    }

    @Transactional
    public void update(Product product) {
        productMapper.updateProduct(product);
    }

    public List<Product> findAdminProductList(String startDate,
                                              String endDate,
                                              String sort,
                                              String dir) {
        return productMapper.findAdminProductList(startDate, endDate, sort, dir);
    }

    @Transactional
    public void decreaseStock(Long productId, Integer qty) {
        productMapper.decreaseStock(productId, qty);
    }

    public List<Product> searchProducts(String keyword) {
        return productMapper.searchProducts(keyword);
    }

    public List<Product> findNewProductLimit(int limit) {
        return productMapper.findNewProductLimit(limit);
    }

    public List<Product> findByCategoryLimit(String category, int limit) {
        return productMapper.findByCategoryLimit(category, limit);
    }
}