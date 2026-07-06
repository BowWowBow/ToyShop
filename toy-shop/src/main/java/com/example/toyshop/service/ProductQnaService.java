package com.example.toyshop.service;

import com.example.toyshop.domain.ProductQna;
import com.example.toyshop.mapper.ProductQnaMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductQnaService {

    private final ProductQnaMapper productQnaMapper;

    public ProductQnaService(ProductQnaMapper productQnaMapper) {
        this.productQnaMapper = productQnaMapper;
    }

    public void saveProductQna(ProductQna productQna) {
        productQna.setAnswerStatus("답변대기");
        productQnaMapper.insertProductQna(productQna);
    }

    public List<ProductQna> findAll() {
        return productQnaMapper.findAll();
    }

    public List<ProductQna> findByMemberId(Long memberId) {
        return productQnaMapper.findByMemberId(memberId);
    }

    public ProductQna findById(Long id) {
        return productQnaMapper.findById(id);
    }

    public void updateAnswer(ProductQna qna){
        productQnaMapper.updateAnswer(qna);
    }
}