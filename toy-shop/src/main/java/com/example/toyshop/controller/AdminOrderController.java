package com.example.toyshop.controller;

import com.example.toyshop.domain.Member;
import com.example.toyshop.domain.Order;
import com.example.toyshop.domain.OrderDetail;
import com.example.toyshop.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/order")
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    private boolean isAdmin(HttpSession session) {

        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return false;
        }

        if (loginMember.getRole() == null) {
            return false;
        }

        return loginMember.getRole().equals("ADMIN");
    }

    @GetMapping("/list")
    public String orderList(@RequestParam(required = false) String startDate,
                            @RequestParam(required = false) String endDate,
                            @RequestParam(required = false, defaultValue = "id") String sort,
                            @RequestParam(required = false, defaultValue = "desc") String dir,
                            HttpSession session,
                            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/member/login";
        }

        if (startDate == null) {
            startDate = "";
        }

        if (endDate == null) {
            endDate = "";
        }

        if (!dir.equals("asc") && !dir.equals("desc")) {
            dir = "desc";
        }

        List<Order> orderList = orderService.findAdminOrderList(startDate, endDate, sort, dir);

        String nextDir = dir.equals("asc") ? "desc" : "asc";

        model.addAttribute("orderList", orderList);
        model.addAttribute("orderCount", orderList.size());
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        model.addAttribute("nextDir", nextDir);

        return "admin/order/list";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id,
                       HttpSession session,
                       Model model) {

        if (!isAdmin(session)) {
            return "redirect:/member/login";
        }

        Order order = orderService.findById(id);

        if (order == null) {
            return "redirect:/admin/order/list";
        }

        List<OrderDetail> detailList = orderService.findOrderItems(id);

        model.addAttribute("order", order);
        model.addAttribute("detailList", detailList);

        return "admin/order/view";
    }

    @PostMapping("/approve/{id}")
    public String approveBankOrder(@PathVariable Long id,
                                   HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/member/login";
        }

        orderService.approveBankOrder(id);

        return "redirect:/admin/order/list";
    }

    @PostMapping("/ready/{id}")
    public String readyOrder(@PathVariable Long id,
                             HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/member/login";
        }

        orderService.updateOrderStatus(id, "READY");

        return "redirect:/admin/order/list";
    }

    @PostMapping("/delivery/{id}")
    public String deliveryOrder(@PathVariable Long id,
                                HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/member/login";
        }

        orderService.updateOrderStatus(id, "DELIVERY");

        return "redirect:/admin/order/list";
    }

    @PostMapping("/done/{id}")
    public String doneOrder(@PathVariable Long id,
                            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/member/login";
        }

        orderService.doneOrder(id);

        return "redirect:/admin/order/list";
    }

    @PostMapping("/rollback/{id}")
    public String rollback(@PathVariable Long id,
                           HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/member/login";
        }

        orderService.rollbackOrder(id);

        return "redirect:/admin/order/list";
    }

    @PostMapping("/delivery-info/{id}")
    public String updateDeliveryInfo(@PathVariable Long id,
                                     @RequestParam String deliveryCompany,
                                     @RequestParam String trackingNo,
                                     HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/member/login";
        }

        orderService.updateDeliveryInfo(id, deliveryCompany, trackingNo);

        return "redirect:/admin/order/list";
    }
}
