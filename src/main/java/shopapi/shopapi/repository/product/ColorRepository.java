package shopapi.shopapi.repository.product;

import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import shopapi.shopapi.models.product.Color;

@Observed
public interface ColorRepository extends JpaRepository<Color,Long> {
}
