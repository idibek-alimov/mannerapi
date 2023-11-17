package shopapi.shopapi.dto.product;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductUpdateDto {
    Long id;
    private Long category;
    private String description;
    private String name;
}
