package shopapi.shopapi.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import shopapi.shopapi.models.order.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {

    @Query(value = "SELECT * FROM item WHERE status=?1",nativeQuery = true)
    List<Item> getByStatus(Integer status);

    @Modifying
    @Query(value = "DELETE FROM item",nativeQuery = true)
    void deleteItems();
}
