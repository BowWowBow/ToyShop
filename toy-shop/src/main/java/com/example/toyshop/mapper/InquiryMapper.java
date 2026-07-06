package com.example.toyshop.mapper;

import com.example.toyshop.domain.Inquiry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InquiryMapper {

    void insertInquiry(Inquiry inquiry);

    List<Inquiry> findAll();

    List<Inquiry> findByMemberId(@Param("memberId") Long memberId);

    Inquiry findById(Long id);

    void updateAnswer(Inquiry inquiry);

}