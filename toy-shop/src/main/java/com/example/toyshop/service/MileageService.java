package com.example.toyshop.service;

import com.example.toyshop.domain.MileageHistory;
import com.example.toyshop.mapper.MileageMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MileageService {

    private final MileageMapper mileageMapper;

    public MileageService(MileageMapper mileageMapper) {
        this.mileageMapper = mileageMapper;
    }

    // 현재 보유 마일리지 조회
    public int getMileage(Long memberId) {

        if (memberId == null) {
            return 0;
        }

        return mileageMapper.findMileageByMemberId(memberId);
    }

    // 회원가입 축하 마일리지 지급
    @Transactional
    public void joinMileage(Long memberId) {

        int amount = 2000;

        mileageMapper.increaseMileage(memberId, amount);

        MileageHistory history = new MileageHistory();
        history.setMemberId(memberId);
        history.setAmount(amount);
        history.setType("JOIN");
        history.setDescription("회원가입 축하 마일리지");

        mileageMapper.insertHistory(history);
    }

    // 주문 시 마일리지 사용
    @Transactional
    public void useMileage(Long memberId, int amount) {

        if (amount <= 0) {
            return;
        }

        int currentMileage = mileageMapper.findMileageByMemberId(memberId);

        if (currentMileage < amount) {
            throw new IllegalArgumentException("보유 마일리지가 부족합니다.");
        }

        mileageMapper.decreaseMileage(memberId, amount);

        MileageHistory history = new MileageHistory();
        history.setMemberId(memberId);
        history.setAmount(-amount);
        history.setType("USE");
        history.setDescription("주문 시 마일리지 사용");

        mileageMapper.insertHistory(history);
    }

    // 주문취소 시 사용 마일리지 복구
    @Transactional
    public void restoreUsedMileage(Long memberId, int amount) {

        if (memberId == null || amount <= 0) {
            return;
        }

        mileageMapper.increaseMileage(memberId, amount);

        MileageHistory history = new MileageHistory();
        history.setMemberId(memberId);
        history.setAmount(amount);
        history.setType("USE_CANCEL");
        history.setDescription("주문취소로 사용 마일리지 복구");

        mileageMapper.insertHistory(history);
    }

    // 구매금액 1% 적립
    @Transactional
    public void orderMileage(Long memberId, int orderPrice) {

        if (orderPrice <= 0) {
            return;
        }

        int amount = orderPrice / 100;

        if (amount <= 0) {
            return;
        }

        mileageMapper.increaseMileage(memberId, amount);

        MileageHistory history = new MileageHistory();
        history.setMemberId(memberId);
        history.setAmount(amount);
        history.setType("ORDER");
        history.setDescription("구매금액 1% 적립");

        mileageMapper.insertHistory(history);
    }

    // 마일리지 내역 조회
    public List<MileageHistory> findHistory(Long memberId) {
        return mileageMapper.findHistoryByMemberId(memberId);
    }

    // 배송완료 되돌리기 시 적립 마일리지 회수
    @Transactional
    public void cancelOrderMileage(Long memberId, int orderPrice) {

        if (memberId == null || orderPrice <= 0) {
            return;
        }

        int amount = orderPrice / 100;

        if (amount <= 0) {
            return;
        }

        mileageMapper.decreaseMileage(memberId, amount);

        MileageHistory history = new MileageHistory();
        history.setMemberId(memberId);
        history.setAmount(-amount);
        history.setType("ORDER_CANCEL");
        history.setDescription("배송완료 되돌리기로 구매 적립 마일리지 회수");

        mileageMapper.insertHistory(history);
    }
}
