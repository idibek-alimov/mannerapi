package shopapi.shopapi.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import shopapi.shopapi.models.order.Item;

public interface ItemRepository extends JpaRepository<Item,Long> {
}
