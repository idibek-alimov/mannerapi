package shopapi.shopapi.dto.article;

import lombok.*;
import shopapi.shopapi.dto.color.ColorDto;
import shopapi.shopapi.dto.inventory.InventoryUpdateDto;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticleSellerUpdateDto {
    private Long id;
    private Long product;
    private ColorDto color;
    private Double price;
    private List<InventoryUpdateDto> inventories;
    private Integer discount;
}
