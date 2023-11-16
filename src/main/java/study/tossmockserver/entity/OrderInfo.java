package study.tossmockserver.entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@Table(name = "order_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderInfo extends BaseTime {

    private static final String ORDER_ID_PREFIX = "ORDER-";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Builder.Default
    @Column(name = "order_id", nullable = false)
    private String orderId = generateOrderId();

    @Column(name = "payment_key")
    private String paymentKey;

    @Column(name = "order_name")
    private String orderName;

    @Column(name = "method")
    private String method;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Builder.Default
    @Column(name = "status", nullable = false)
    private String status = OrderStatus.READY.getStatusName();

    @Column(name = "requested_at")
    private LocalDateTime requestedAt;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "last_transaction_key")
    private String lastTransactionKey;

    @SuppressWarnings("java:S107")
    protected OrderInfo(Long id, User user, Product product, String orderId, String paymentKey, String orderName, String method, Integer quantity, BigDecimal totalAmount, String status, LocalDateTime requestedAt, LocalDateTime approvedAt, String lastTransactionKey) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.orderId = orderId;
        this.paymentKey = paymentKey;
        this.orderName = orderName;
        this.method = method;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.status = status;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
        this.lastTransactionKey = lastTransactionKey;
    }

    private static String generateOrderId() {
        return ORDER_ID_PREFIX + System.currentTimeMillis();
    }
    @Getter
    enum OrderStatus {
        READY("READY"), CANCELED("CANCELED"), DONE("DONE"), IN_PROGRESS("IN_PROGRESS");

        private final String statusName;

        OrderStatus(String statusName) {
            this.statusName = statusName;
        }
    }
}
