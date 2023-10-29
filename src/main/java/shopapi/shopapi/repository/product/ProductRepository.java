package shopapi.shopapi.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import shopapi.shopapi.models.product.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
