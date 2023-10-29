package shopapi.shopapi.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import shopapi.shopapi.models.product.Category;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
