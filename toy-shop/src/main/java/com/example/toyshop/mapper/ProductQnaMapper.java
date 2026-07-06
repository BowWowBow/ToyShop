package com.example.toyshop.mapper;

import com.example.toyshop.domain.ProductQna;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductQnaMapper {

    void insertProductQna(ProductQna productQna);

    List<ProductQna> findAll();

    ProductQna findById(Long id);

    List<ProductQna> findByMemberId(@Param("memberId") Long memberId);

    void updateAnswer(ProductQna productQna);
}