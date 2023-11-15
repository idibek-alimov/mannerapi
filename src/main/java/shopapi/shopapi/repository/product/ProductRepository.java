package shopapi.shopapi.repository.product;

import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shopapi.shopapi.models.product.Product;

import java.util.List;

@Observed
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "SELECT * FROM product WHERE user_id=?1",nativeQuery = true)
    List<Product> findByUserId(Long id);
}
