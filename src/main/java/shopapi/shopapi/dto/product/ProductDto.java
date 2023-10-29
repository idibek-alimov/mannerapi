package shopapi.shopapi.dto.product;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {
    Long id;
    String name;
    String description;
    Long category;
}
