package shopapi.shopapi.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import shopapi.shopapi.models.order.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
