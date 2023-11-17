package shopapi.shopapi.dto.article;

import lombok.*;
import shopapi.shopapi.dto.inventory.InventoryCreateDto;
import shopapi.shopapi.dto.inventory.InventoryUpdateDto;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticleUpdateDto{
    private Long id;
    private Long product;
    private Long color;
    private Double price;
    private List<InventoryUpdateDto> inventories;
    private Integer discount;
}
