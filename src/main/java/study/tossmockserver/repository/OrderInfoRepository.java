package study.tossmockserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import study.tossmockserver.entity.OrderInfo;

import java.util.Optional;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {

    @Query("select o from OrderInfo o join fetch o.product join fetch o.user where o.orderId = :orderId")
    Optional<OrderInfo> findByOrderIdWithProductAndUser(String orderId);
}
