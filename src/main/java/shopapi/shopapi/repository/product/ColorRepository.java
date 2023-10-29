package shopapi.shopapi.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import shopapi.shopapi.models.product.Color;

public interface ColorRepository extends JpaRepository<Color,Long> {
}
