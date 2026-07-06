package com.example.toyshop.controller;

import com.example.toyshop.domain.Member;
import com.example.toyshop.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminMemberController {

    private final MemberService memberService;

    public AdminMemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/admin/member/list")
    public String memberList(@RequestParam(required = false) String startDate,
                             @RequestParam(required = false) String endDate,
                             @RequestParam(required = false, defaultValue = "id") String sort,
                             @RequestParam(required = false, defaultValue = "asc") String dir,
                             HttpSession session,
                             Model model) {

        String loginRole = (String) session.getAttribute("loginRole");

        if (loginRole == null || !loginRole.equals("ADMIN")) {
            return "redirect:/member/login";
        }

        if (startDate == null) {
            startDate = "";
        }

        if (endDate == null) {
            endDate = "";
        }

        if (!dir.equals("asc") && !dir.equals("desc")) {
            dir = "asc";
        }

        List<Member> memberList =
                memberService.findAdminMemberList(startDate, endDate, sort, dir);

        String nextDir = dir.equals("asc") ? "desc" : "asc";

        model.addAttribute("memberList", memberList);
        model.addAttribute("memberCount", memberList.size());
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        model.addAttribute("nextDir", nextDir);

        return "admin/member/list";
    }
}