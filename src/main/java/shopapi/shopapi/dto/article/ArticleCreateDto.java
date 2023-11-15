package shopapi.shopapi.dto.article;

import lombok.*;
import shopapi.shopapi.dto.inventory.InventoryCreateDto;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticleCreateDto {
    private Long product;
    private Long color;
    private Double price;
    private List<InventoryCreateDto> inventories;
    private Integer discount;
}
