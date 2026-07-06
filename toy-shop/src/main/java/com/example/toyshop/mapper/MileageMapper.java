package com.example.toyshop.mapper;

import com.example.toyshop.domain.MileageHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MileageMapper {

    // 회원 마일리지 증가
    void increaseMileage(@Param("memberId") Long memberId,
                         @Param("amount") int amount);

    // 회원 마일리지 차감
    void decreaseMileage(@Param("memberId") Long memberId,
                         @Param("amount") int amount);

    // 현재 보유 마일리지 조회
    int findMileageByMemberId(Long memberId);

    // 마일리지 내역 저장
    void insertHistory(MileageHistory mileageHistory);

    // 회원별 마일리지 내역 조회
    List<MileageHistory> findHistoryByMemberId(Long memberId);
}