package study.tossmockserver.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class TossConfirmRequest {

    private final String orderId;
    private final BigDecimal amount;
    private final String paymentKey;
}
