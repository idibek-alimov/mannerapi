package shopapi.shopapi.repository.product;

//import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shopapi.shopapi.models.product.Category;

import java.util.List;

//@Observed
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query(value = "SELECT * FROM category WHERE category_id IS NULL",nativeQuery = true)
    List<Category> getParentCategories();

    @Query(value = "SELECT * FROM category WHERE category_id=?1",nativeQuery = true)
    List<Category> getCategoryByParent(Long id);
}
