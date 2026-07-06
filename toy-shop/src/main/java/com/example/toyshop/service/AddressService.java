package com.example.toyshop.service;

import com.example.toyshop.domain.Address;
import com.example.toyshop.mapper.AddressMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {

    private final AddressMapper addressMapper;

    public AddressService(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public List<Address> findByMemberId(Long memberId) {
        return addressMapper.findByMemberId(memberId);
    }

    public Address findDefaultAddress(Long memberId) {
        return addressMapper.findDefaultAddress(memberId);
    }

    public Address findByIdAndMemberId(Long id, Long memberId) {
        return addressMapper.findByIdAndMemberId(id, memberId);
    }

    @Transactional
    public void save(Address address) {

        addressMapper.clearDefaultAddress(address.getMemberId());

        address.setIsDefault("Y");
        address.setDefaultYn("Y");

        addressMapper.insertAddress(address);
    }

    @Transactional
    public void update(Address address) {

        Address savedAddress =
                addressMapper.findByIdAndMemberId(
                        address.getId(),
                        address.getMemberId()
                );

        if (savedAddress == null) {
            return;
        }

        address.setIsDefault(savedAddress.getIsDefault());
        address.setDefaultYn(savedAddress.getDefaultYn());

        addressMapper.updateAddress(address);
    }

    @Transactional
    public void delete(Long id, Long memberId) {
        addressMapper.deleteAddress(id, memberId);
    }

    @Transactional
    public void changeDefault(Long memberId,
                              Long addressId) {

        Address savedAddress =
                addressMapper.findByIdAndMemberId(
                        addressId,
                        memberId
                );

        if (savedAddress == null) {
            return;
        }

        addressMapper.clearDefaultAddress(memberId);

        addressMapper.updateDefaultAddress(addressId);
    }
}
