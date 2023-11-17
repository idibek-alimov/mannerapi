package shopapi.shopapi.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateDto extends ProductCreateDto{
    Long id;
}
