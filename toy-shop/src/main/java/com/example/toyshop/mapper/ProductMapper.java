package com.example.toyshop.mapper;

import com.example.toyshop.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    List<Product> findAll();

    List<Product> findByCategory(String category);

    Product findById(Long id);

    void insertProduct(Product product);

    void updateProduct(Product product);

    List<Product> searchByName(String keyword);

    List<Product> findAdminProductList(@Param("startDate") String startDate,
                                       @Param("endDate") String endDate,
                                       @Param("sort") String sort,
                                       @Param("dir") String dir);

    int decreaseStock(@Param("productId") Long productId,
                      @Param("qty") int qty);

    // 주문취소 시 재고 복구
    int increaseStock(@Param("productId") Long productId,
                      @Param("qty") int qty);

    List<Product> searchProducts(String keyword);

    List<Product> findNewProductLimit(@Param("limit") int limit);

    List<Product> findByCategoryLimit(@Param("category") String category,
                                      @Param("limit") int limit);
}
