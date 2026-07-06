package com.example.toyshop.service;

import com.example.toyshop.domain.Address;
import com.example.toyshop.domain.Member;
import com.example.toyshop.mapper.AddressMapper;
import com.example.toyshop.mapper.MemberMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class MemberService {

    private final MemberMapper memberMapper;
    private final AddressMapper addressMapper;
    private final MileageService mileageService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberMapper memberMapper,
                         AddressMapper addressMapper,
                         MileageService mileageService,
                         MailService mailService,
                         PasswordEncoder passwordEncoder) {
        this.memberMapper = memberMapper;
        this.addressMapper = addressMapper;
        this.mileageService = mileageService;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void join(Member member) {

        Member existingMember = memberMapper.findByLoginId(member.getLoginId());

        if (existingMember != null) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);

        memberMapper.insertMember(member);

        Member savedMember = memberMapper.findByLoginId(member.getLoginId());

        Address address = new Address();
        address.setMemberId(savedMember.getId());
        address.setReceiverName(savedMember.getName());
        address.setReceiverPhone(savedMember.getPhone());
        address.setZipcode(savedMember.getZipcode());
        address.setAddress(savedMember.getAddress());
        address.setAddressDetail(savedMember.getAddressDetail());
        address.setIsDefault("Y");
        address.setDefaultYn("Y");

        addressMapper.insertAddress(address);

        mileageService.joinMileage(savedMember.getId());
    }

    public Member login(String loginId, String password) {

        Member member = memberMapper.findByLoginId(loginId);

        if (member == null) {
            return null;
        }

        if (!passwordEncoder.matches(password, member.getPassword())) {
            return null;
        }

        return member;
    }

    public Member findById(Long id) {
        return memberMapper.findById(id);
    }

    @Transactional
    public void updateMember(Member member) {
        memberMapper.updateMember(member);
    }

    @Transactional
    public void dormantMember(Long id) {
        memberMapper.dormantMember(id);
    }

    @Transactional
    public void releaseDormant(Long id) {
        memberMapper.releaseDormant(id);
    }

    public boolean sendLoginIdByEmail(String name, String email) {

        Member member = memberMapper.findIdByNameAndEmail(name, email);

        if (member == null) {
            return false;
        }

        mailService.sendFindIdMail(email, member.getLoginId());

        return true;
    }

    @Transactional
    public boolean resetPasswordAndSendEmail(String loginId, String name, String email) {

        Member member = memberMapper.findPasswordMember(loginId, name, email);

        if (member == null) {
            return false;
        }

        String tempPassword = createTempPassword();

        String encodedTempPassword = passwordEncoder.encode(tempPassword);

        memberMapper.updatePassword(member.getId(), encodedTempPassword);

        mailService.sendTempPasswordMail(email, tempPassword);

        return true;
    }

    private String createTempPassword() {

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }

        return sb.toString();
    }

    @Transactional
    public boolean changePassword(Long memberId,
                                  String currentPassword,
                                  String newPassword) {

        Member member = memberMapper.findById(memberId);

        if (member == null) {
            return false;
        }

        if (!passwordEncoder.matches(currentPassword, member.getPassword())) {
            return false;
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);

        memberMapper.changePassword(memberId, encodedNewPassword);

        return true;
    }

    public List<Member> findAll() {
        return memberMapper.findAll();
    }

    public List<Member> findAdminMemberList(String startDate,
                                            String endDate,
                                            String sort,
                                            String dir) {
        return memberMapper.findAdminMemberList(startDate, endDate, sort, dir);
    }
}