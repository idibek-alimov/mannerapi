package shopapi.shopapi.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import shopapi.shopapi.models.order.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {

    @Query(value = "SELECT * FROM item WHERE status=?1",nativeQuery = true)
    List<Item> getByStatus(Integer status);

    @Query(value = "SELECT * FROM item WHERE status=?1 AND customer_id=?2",nativeQuery = true)
    List<Item> getByStatusAndUser(Integer status,Long userId);

    @Query(value = "SELECT * FROM item WHERE (status=0 OR status=1)  AND customer_id=?1",nativeQuery = true)
    List<Item> getCustomerShippingItems(Long userId);

    @Modifying
    @Query(value = "DELETE FROM item",nativeQuery = true)
    void deleteItems();

    @Modifying
    @Query(value = "UPDATE item SET status=?2 WHERE id=?1",nativeQuery = true)
    void changeStatus(Long itemId,Integer status);
}
