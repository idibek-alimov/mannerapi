package shopapi.shopapi.dto.product;

import lombok.*;
import shopapi.shopapi.dto.category.CategoryUpdateDto;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SellerProductDto {
    Long id;
    String name;
    String description;
    CategoryUpdateDto category;
}
