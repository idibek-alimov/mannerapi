package shopapi.shopapi.repository.order;

import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import shopapi.shopapi.models.order.Order;

@Observed
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Modifying
    @Query(value = "DELETE FROM orders",nativeQuery = true)
    void deleteOrders();
}
