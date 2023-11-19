package shopapi.shopapi.repository.product;

//import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import shopapi.shopapi.dto.inventory.InventoryItemOrderDto;
import shopapi.shopapi.models.product.Inventory;

import java.util.List;

//@Observed
public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    @Query(value = "SELECT * FROM inventory WHERE article_id=?1 AND available IS TRUE",nativeQuery = true)
    List<Inventory> getByArticleId(Long id);

    @Query(value = "SELECT inventory.*,address.address_line,address.latitude,address.longitude,item.quantity as item_quantity " +
            "FROM inventory JOIN item ON item.inventory_id = inventory.id " +
            "JOIN orders ON orders.id = item.order_id " +
            "JOIN address ON address.id = orders.address_id " +
            "WHERE item.status=0",nativeQuery = true)
    List<InventoryItemOrderDto> findQueueInventories();
    @Query(value = "select inventory.* from inventory" +
            " inner join item ON item.inventory_id = inventory.id " +
            "where item.status=1",nativeQuery = true)
    List<Inventory> findShippingInventories();
    @Query(value = "select inventory.* from inventory" +
            " inner join item ON item.inventory_id = inventory.id " +
            "where item.status=2",nativeQuery = true)
    List<Inventory> findDeliveredInventories();



    @Query(value = "select inventory.* from inventory" +
            " inner join item ON item.inventory_id = inventory.id " +
            "where item.customer_id=?1 and (item.status=1 OR item.status=2)",nativeQuery = true)
    List<Inventory> findShippingInventoriesByUser(Long userId);
    @Query(value = "select inventory.* from inventory" +
            " inner join item ON item.inventory_id = inventory.id " +
            "where item.customer_id=?1 and item.status=2",nativeQuery = true)
    List<Inventory> findDeliveredInventoriesByUser(Long userId);

    @Modifying
    @Query(value ="UPDATE inventory set available=False WHERE article_id=?2 AND id NOT IN ?1",nativeQuery = true)
    void setUnavailableByArticleId(List<Long> ids,Long articleId);


}
