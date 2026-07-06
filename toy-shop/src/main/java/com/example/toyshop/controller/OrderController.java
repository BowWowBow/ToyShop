package com.example.toyshop.controller;

import com.example.toyshop.domain.Address;
import com.example.toyshop.domain.Cart;
import com.example.toyshop.domain.Member;
import com.example.toyshop.domain.Order;
import com.example.toyshop.domain.OrderDetail;
import com.example.toyshop.service.AddressService;
import com.example.toyshop.service.CartService;
import com.example.toyshop.service.MileageService;
import com.example.toyshop.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;
    private final MileageService mileageService;
    private final AddressService addressService;

    public OrderController(OrderService orderService,
                           CartService cartService,
                           MileageService mileageService,
                           AddressService addressService) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.mileageService = mileageService;
        this.addressService = addressService;
    }

    @GetMapping("/order")
    public String orderList(@RequestParam(required = false) String startDate,
                            @RequestParam(required = false) String endDate,
                            HttpSession session,
                            Model model) {

        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        List<Order> orderList =
                orderService.findByMemberIdAndDate(
                        loginMember.getId(),
                        startDate,
                        endDate
                );

        model.addAttribute("orderList", orderList);
        model.addAttribute("orderCount", orderList.size());
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "order/order";
    }

    @GetMapping("/order/pay")
    public String orderPayPage(HttpSession session,
                               Model model) {

        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        List<Cart> cartList =
                cartService.findAllByMemberId(loginMember.getId());

        int totalPrice = 0;

        for (Cart cart : cartList) {
            totalPrice += cart.getPrice() * cart.getQty();
        }

        int deliveryFee = cartList.isEmpty() ? 0 : 3000;
        int finalPrice = totalPrice + deliveryFee;

        int mileage = mileageService.getMileage(loginMember.getId());

        List<Address> addressList =
                addressService.findByMemberId(
                        loginMember.getId()
                );

        Address defaultAddress =
                addressService.findDefaultAddress(
                        loginMember.getId()
                );

        model.addAttribute("loginMember", loginMember);
        model.addAttribute("cartList", cartList);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("deliveryFee", deliveryFee);
        model.addAttribute("finalPrice", finalPrice);
        model.addAttribute("mileage", mileage);
        model.addAttribute("addressList", addressList);
        model.addAttribute("defaultAddress", defaultAddress);

        return "order/pay";
    }

    @PostMapping("/order/all")
    public String orderAll(Order order,
                           HttpSession session) {

        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        Long orderId =
                orderService.orderAll(
                        order,
                        loginMember.getId()
                );

        if (orderId == null) {
            return "redirect:/cart";
        }

        return "redirect:/order/complete/" + orderId;
    }

    @GetMapping("/order/complete/{id}")
    public String orderComplete(@PathVariable("id") Long id,
                                HttpSession session,
                                Model model) {

        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        Order order = orderService.findById(id);

        if (order == null) {
            return "redirect:/order";
        }

        if (order.getMemberId() == null ||
                !order.getMemberId().equals(loginMember.getId())) {
            return "redirect:/order";
        }

        List<OrderDetail> orderItemList =
                orderService.findOrderItems(id);

        model.addAttribute("order", order);
        model.addAttribute("orderItemList", orderItemList);

        return "order/complete";
    }

    @PostMapping("/order/cancel/{id}")
    public String cancelOrder(@PathVariable Long id,
                              HttpSession session) {

        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        orderService.cancelOrderByMember(id, loginMember.getId());

        return "redirect:/order";
    }
}
