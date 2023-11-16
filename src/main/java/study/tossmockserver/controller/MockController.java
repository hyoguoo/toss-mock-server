package study.tossmockserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.tossmockserver.dto.TossConfirmRequest;
import study.tossmockserver.dto.TossPaymentResponse;
import study.tossmockserver.entity.OrderInfo;
import study.tossmockserver.repository.OrderInfoRepository;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class MockController {

    private static final String PAYMNET_KEY = "test_payment_key";
    private final OrderInfoRepository orderInfoRepository;

    @GetMapping("/orders/{orderId}")
    public TossPaymentResponse getPaymentInfoByOrderId(@PathVariable("orderId") String orderId) {
        OrderInfo orderInfo = this.orderInfoRepository.findByOrderIdWithProductAndUser(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 정보가 존재하지 않습니다."));
        BigDecimal totalAmount = orderInfo.getTotalAmount();

        return TossPaymentResponse.builder()
                .version("2022-11-16")
                .paymentKey(PAYMNET_KEY)
                .type("NORMAL")
                .orderId(orderId)
                .orderName(orderInfo.getOrderName())
                .currency("KRW")
                .method("카드")
                .totalAmount(totalAmount.longValue())
                .balanceAmount(totalAmount.longValue())
                .status("IN_PROGRESS")
                .requestedAt("2023-11-07T00:03:16+09:00")
                .approvedAt("2023-11-07T00:03:39+09:00")
                .lastTransactionKey("testogu59fri8fgjrikf")
                .suppliedAmount(totalAmount.multiply(BigDecimal.valueOf(0.9)).longValue())
                .vat(totalAmount.multiply(BigDecimal.valueOf(0.1)).longValue())
                .receipt(new TossPaymentResponse.Receipt("https://dashboard.tosspayments.com/receipt/test"))
                .checkout(new TossPaymentResponse.Checkout("https://api.tosspayments.com/v1/payments/test"))
                .country("KR")
                .build();
    }

    @PostMapping("/confirm")
    public TossPaymentResponse confirmPayment(@RequestBody TossConfirmRequest tossConfirmRequest) {
        String paymentKey = tossConfirmRequest.getPaymentKey();
        String orderId = tossConfirmRequest.getOrderId();
        BigDecimal totalAmount = tossConfirmRequest.getAmount();

        return TossPaymentResponse.builder()
                .version("2022-11-16")
                .paymentKey(paymentKey)
                .type("NORMAL")
                .orderId(orderId)
                .orderName("orderName")
                .currency("KRW")
                .method("카드")
                .totalAmount(totalAmount.longValue())
                .balanceAmount(totalAmount.longValue())
                .status("DONE")
                .requestedAt("2023-11-07T00:03:16+09:00")
                .approvedAt("2023-11-07T00:03:39+09:00")
                .lastTransactionKey("testogu59fri8fgjrikf")
                .suppliedAmount(totalAmount.multiply(BigDecimal.valueOf(0.9)).longValue())
                .vat(totalAmount.multiply(BigDecimal.valueOf(0.1)).longValue())
                .receipt(new TossPaymentResponse.Receipt("https://dashboard.tosspayments.com/receipt/test"))
                .checkout(new TossPaymentResponse.Checkout("https://api.tosspayments.com/v1/payments/test"))
                .country("KR")
                .build();
    }
}
