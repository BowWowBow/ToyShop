package com.example.toyshop.mapper;

import com.example.toyshop.domain.RecentProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecentProductMapper {

    void deleteRecent(RecentProduct recentProduct);

    void insertRecent(RecentProduct recentProduct);

    List<RecentProduct> findRecentList(Long memberId);
}