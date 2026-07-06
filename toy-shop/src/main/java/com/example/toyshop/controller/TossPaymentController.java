package com.example.toyshop.controller;

import com.example.toyshop.domain.Member;
import com.example.toyshop.domain.Order;
import com.example.toyshop.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Controller
public class TossPaymentController {

    @Value("${toss.secret-key}")
    private String tossSecretKey;

    private final OrderService orderService;

    public TossPaymentController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/toss/success")
    public String tossSuccess(@RequestParam String paymentKey,
                              @RequestParam String orderId,
                              @RequestParam int amount,
                              @RequestParam(required = false) String orderName,
                              @RequestParam(required = false) String orderPhone,
                              @RequestParam(required = false) String receiverName,
                              @RequestParam(required = false) String receiverPhone,
                              @RequestParam(required = false) String address,
                              @RequestParam(required = false) String deliveryMessage,
                              @RequestParam(required = false, defaultValue = "0") Integer useMileage,
                              HttpSession session) throws Exception {

        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        String auth = Base64.getEncoder()
                .encodeToString((tossSecretKey + ":").getBytes(StandardCharsets.UTF_8));

        String jsonBody = "{"
                + "\"paymentKey\":\"" + paymentKey + "\","
                + "\"orderId\":\"" + orderId + "\","
                + "\"amount\":" + amount
                + "}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.tosspayments.com/v1/payments/confirm"))
                .header("Authorization", "Basic " + auth)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            return "redirect:/toss/fail?message=결제승인실패";
        }

        Order order = new Order();

        order.setOrderName(orderName);
        order.setOrderPhone(orderPhone);
        order.setReceiverName(receiverName);
        order.setReceiverPhone(receiverPhone);
        order.setAddress(address);
        order.setDeliveryMessage(deliveryMessage);

        // 카드결제 성공 후에도 주문/결제 화면에서 사용한 마일리지를 OrderService로 전달
        order.setUseMileage(useMileage);

        order.setPaymentMethod("CARD");
        order.setPaymentStatus("PAID");
        order.setTossPaymentKey(paymentKey);

        Long savedOrderId = orderService.orderAll(order, loginMember.getId());

        if (savedOrderId == null) {
            return "redirect:/cart";
        }

        return "redirect:/order/complete/" + savedOrderId;
    }

    @GetMapping("/toss/fail")
    public String tossFail(@RequestParam(required = false) String code,
                           @RequestParam(required = false) String message) {
        return "redirect:/order/pay";
    }
}
