package com.example.toyshop.mapper;

import com.example.toyshop.domain.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AddressMapper {

    void insertAddress(Address address);

    List<Address> findByMemberId(Long memberId);

    Address findDefaultAddress(Long memberId);

    Address findByIdAndMemberId(@Param("id") Long id,
                                @Param("memberId") Long memberId);

    void updateAddress(Address address);

    void deleteAddress(@Param("id") Long id,
                       @Param("memberId") Long memberId);

    void clearDefaultAddress(Long memberId);

    void updateDefaultAddress(Long id);
}
