package com.example.toyshop.controller;

import com.example.toyshop.domain.Member;
import com.example.toyshop.mapper.MemberMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/password")
public class AdminPasswordController {

    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AdminPasswordController(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @GetMapping
    public String form(HttpSession session) {
        String role = (String) session.getAttribute("loginRole");

        if (!"ADMIN".equals(role)) {
            return "redirect:/member/login";
        }

        return "admin/password";
    }

    @PostMapping
    public String change(@RequestParam String currentPassword,
                         @RequestParam String newPassword,
                         @RequestParam String newPasswordConfirm,
                         HttpSession session,
                         Model model) {

        String role = (String) session.getAttribute("loginRole");
        String loginId = (String) session.getAttribute("loginId");

        if (!"ADMIN".equals(role) || loginId == null) {
            return "redirect:/member/login";
        }

        if (!newPassword.equals(newPasswordConfirm)) {
            model.addAttribute("error", "새 비밀번호가 일치하지 않습니다.");
            return "admin/password";
        }

        Member admin = memberMapper.findByLoginId(loginId);

        if (admin == null) {
            model.addAttribute("error", "관리자 정보를 찾을 수 없습니다.");
            return "admin/password";
        }

        if (!passwordEncoder.matches(currentPassword, admin.getPassword())) {
            model.addAttribute("error", "현재 비밀번호가 맞지 않습니다.");
            return "admin/password";
        }

        String encodedPassword = passwordEncoder.encode(newPassword);

        memberMapper.updatePassword(admin.getId(), encodedPassword);

        model.addAttribute("message", "비밀번호가 변경되었습니다.");
        return "admin/password";
    }
}