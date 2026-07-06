package com.example.toyshop.service;

import com.example.toyshop.domain.Inquiry;
import com.example.toyshop.mapper.InquiryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InquiryService {

    private final InquiryMapper inquiryMapper;

    public InquiryService(InquiryMapper inquiryMapper) {
        this.inquiryMapper = inquiryMapper;
    }

    public void saveInquiry(Inquiry inquiry) {

        if (inquiry.getMailReceive() == null) {
            inquiry.setMailReceive("N");
        } else {
            inquiry.setMailReceive("Y");
        }

        inquiry.setAnswerStatus("답변대기");

        inquiryMapper.insertInquiry(inquiry);
    }

    public List<Inquiry> findAll() {
        return inquiryMapper.findAll();
    }

    public List<Inquiry> findByMemberId(Long memberId) {
        return inquiryMapper.findByMemberId(memberId);
    }

    public Inquiry findById(Long id) {
        return inquiryMapper.findById(id);
    }
}