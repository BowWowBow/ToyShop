package com.example.toyshop.controller;

import com.example.toyshop.domain.Member;
import com.example.toyshop.domain.Order;
import com.example.toyshop.service.MemberService;
import com.example.toyshop.service.MileageService;
import com.example.toyshop.service.OrderService;
import com.example.toyshop.service.RecentProductService;
import com.example.toyshop.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final OrderService orderService;
    private final MileageService mileageService;
    private final WishlistService wishlistService;
    private final RecentProductService recentProductService;

    public MemberController(MemberService memberService,
                            OrderService orderService,
                            MileageService mileageService,
                            WishlistService wishlistService,
                            RecentProductService recentProductService) {
        this.memberService = memberService;
        this.orderService = orderService;
        this.mileageService = mileageService;
        this.wishlistService = wishlistService;
        this.recentProductService = recentProductService;
    }

    @GetMapping("/join")
    public String join() {
        return "member/join";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "member/join-form";
    }

    @PostMapping("/join-save")
    public String joinSave(@ModelAttribute Member member,
                           @RequestParam String emailId,
                           @RequestParam String emailDomain,
                           @RequestParam String phone1,
                           @RequestParam String phone2,
                           @RequestParam String phone3,
                           @RequestParam String address1,
                           @RequestParam String address2,
                           @RequestParam String zipcode,
                           RedirectAttributes rttr) {

        try {
            member.setEmail(emailId + "@" + emailDomain);
            member.setPhone(phone1 + "-" + phone2 + "-" + phone3);
            member.setAddress(address1);
            member.setAddressDetail(address2);
            member.setZipcode(zipcode);

            memberService.join(member);

        } catch (IllegalArgumentException e) {
            rttr.addFlashAttribute("error", e.getMessage());
            return "redirect:/member/join-form";
        }

        return "redirect:/member/join-complete";
    }

    @GetMapping("/join-complete")
    public String joinComplete() {
        return "member/join-complete";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "member/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String loginId,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        Member loginMember = memberService.login(loginId, password);

        if (loginMember == null) {
            model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "member/login";
        }

        if ("DORMANT".equals(loginMember.getStatus())) {
            model.addAttribute("dormantMember", loginMember);
            return "member/dormant-login";
        }

        session.setAttribute("loginMember", loginMember);
        session.setAttribute("loginId", loginMember.getLoginId());
        session.setAttribute("loginName", loginMember.getName());
        session.setAttribute("loginRole", loginMember.getRole());

        return "redirect:/";
    }

    @GetMapping("/find-id")
    public String findIdForm() {
        return "member/find-id";
    }

    @PostMapping("/find-id")
    public String findId(@RequestParam String name,
                         @RequestParam String email,
                         Model model) {

        boolean result = memberService.sendLoginIdByEmail(name, email);

        if (!result) {
            model.addAttribute("error", "일치하는 회원 정보가 없습니다.");
            return "member/find-id";
        }

        model.addAttribute("msg", "가입된 이메일로 아이디가 발송되었습니다.");

        return "member/find-id";
    }

    @GetMapping("/find-password")
    public String findPasswordForm() {
        return "member/find-password";
    }

    @PostMapping("/find-password")
    public String findPassword(@RequestParam String loginId,
                               @RequestParam String name,
                               @RequestParam String email,
                               Model model) {

        boolean result = memberService.resetPasswordAndSendEmail(loginId, name, email);

        if (!result) {
            model.addAttribute("error", "일치하는 회원 정보가 없습니다.");
            return "member/find-password";
        }

        model.addAttribute("msg", "가입된 이메일로 임시 비밀번호가 발송되었습니다.");

        return "member/find-password";
    }

    @PostMapping("/dormant-release")
    public String dormantRelease(@RequestParam Long id,
                                 HttpSession session,
                                 RedirectAttributes rttr) {

        memberService.releaseDormant(id);

        Member releasedMember = memberService.findById(id);

        session.setAttribute("loginMember", releasedMember);
        session.setAttribute("loginId", releasedMember.getLoginId());
        session.setAttribute("loginName", releasedMember.getName());
        session.setAttribute("loginRole", releasedMember.getRole());

        rttr.addFlashAttribute("msg", "휴면 상태가 해제되었습니다.");

        return "redirect:/";
    }

    @GetMapping("/mypage")
    public String mypage(HttpSession session,
                         Model model) {

        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        Long memberId = loginMember.getId();

        // 중요: 전체 주문이 아니라 로그인한 회원 주문만 조회
        List<Order> orderList = orderService.findByMemberId(memberId);

        model.addAttribute("loginMember", loginMember);
        model.addAttribute("orderList", orderList);
        model.addAttribute("orderCount", orderList.size());

        model.addAttribute("mileage", mileageService.getMileage(memberId));
        model.addAttribute("wishlistCount", wishlistService.findByMemberId(memberId).size());
        model.addAttribute("recentCount", recentProductService.getRecentProductList(memberId).size());

        return "member/mypage";
    }

    @GetMapping("/mileage")
    public String mileage(HttpSession session,
                          Model model) {

        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        Long memberId = loginMember.getId();

        model.addAttribute("mileage", mileageService.getMileage(memberId));
        model.addAttribute("historyList", mileageService.findHistory(memberId));

        return "member/mileage";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Member member,
                       HttpSession session,
                       RedirectAttributes rttr) {

        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        member.setId(loginMember.getId());

        memberService.updateMember(member);

        Member updatedMember = memberService.findById(loginMember.getId());

        session.setAttribute("loginMember", updatedMember);
        session.setAttribute("loginId", updatedMember.getLoginId());
        session.setAttribute("loginName", updatedMember.getName());
        session.setAttribute("loginRole", updatedMember.getRole());

        rttr.addFlashAttribute("msg", "회원정보가 수정되었습니다.");

        return "redirect:/member/mypage";
    }

    @GetMapping("/dormant")
    public String dormantForm(HttpSession session) {

        if (session.getAttribute("loginMember") == null) {
            return "redirect:/member/login";
        }

        return "member/dormant";
    }

    @PostMapping("/dormant")
    public String dormant(HttpSession session) {

        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        memberService.dormantMember(loginMember.getId());

        session.invalidate();

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/change-password")
    public String changePasswordForm(HttpSession session) {

        if (session.getAttribute("loginMember") == null) {
            return "redirect:/member/login";
        }

        return "member/change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 HttpSession session,
                                 Model model) {

        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "새 비밀번호가 일치하지 않습니다.");
            return "member/change-password";
        }

        boolean result = memberService.changePassword(
                loginMember.getId(),
                currentPassword,
                newPassword
        );

        if (!result) {
            model.addAttribute("error", "현재 비밀번호가 일치하지 않습니다.");
            return "member/change-password";
        }

        session.invalidate();

        return "redirect:/member/login?changed=true";
    }
}