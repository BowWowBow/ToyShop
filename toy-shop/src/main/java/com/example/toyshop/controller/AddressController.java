package com.example.toyshop.controller;

import com.example.toyshop.domain.Address;
import com.example.toyshop.domain.Member;
import com.example.toyshop.service.AddressService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/list")
    public String list(HttpSession session,
                       Model model) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        model.addAttribute(
                "addressList",
                addressService.findByMemberId(
                        loginMember.getId()
                )
        );

        return "address/list";
    }

    @PostMapping("/save")
    public String save(Address address,
                       HttpSession session) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        address.setMemberId(
                loginMember.getId()
        );

        addressService.save(address);

        return "redirect:/address/list";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id,
                           HttpSession session,
                           Model model) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        Address address =
                addressService.findByIdAndMemberId(
                        id,
                        loginMember.getId()
                );

        if (address == null) {
            return "redirect:/address/list";
        }

        model.addAttribute("address", address);

        return "address/edit";
    }

    @PostMapping("/update")
    public String update(Address address,
                         HttpSession session) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        address.setMemberId(loginMember.getId());

        // 핵심: 배송지 전화번호는 항상 회원정보 전화번호로 저장
        address.setReceiverPhone(loginMember.getPhone());

        addressService.update(address);

        return "redirect:/address/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         HttpSession session) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        addressService.delete(
                id,
                loginMember.getId()
        );

        return "redirect:/address/list";
    }

    @GetMapping("/default/{id}")
    public String changeDefault(@PathVariable Long id,
                                HttpSession session) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        addressService.changeDefault(
                loginMember.getId(),
                id
        );

        return "redirect:/address/list";
    }

    @GetMapping("/recent")
    public String recent(HttpSession session,
                         Model model) {

        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        model.addAttribute("addressList",
                addressService.findByMemberId(loginMember.getId()));

        return "address/recent";
    }
}
