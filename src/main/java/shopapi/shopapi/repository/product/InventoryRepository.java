package shopapi.shopapi.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shopapi.shopapi.models.product.Inventory;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    @Query(value = "SELECT * FROM inventory WHERE article_id=?1",nativeQuery = true)
    List<Inventory> getByArticleId(Long id);

    @Query(value = "select inventory.* from inventory" +
            " inner join item ON item.inventory_id = inventory.id " +
            "where item.customer_id=?1 and item.status=0",nativeQuery = true)
    List<Inventory> findShippingInventories(Long userId);
    @Query(value = "select inventory.* from inventory" +
            " inner join item ON item.inventory_id = inventory.id " +
            "where item.customer_id=?1 and item.status=1",nativeQuery = true)
    List<Inventory> findDeliveredInventories(Long userId);
}
