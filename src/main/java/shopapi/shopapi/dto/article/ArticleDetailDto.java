package shopapi.shopapi.dto.article;

import lombok.*;
import shopapi.shopapi.dto.inventory.InventoryDto;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticleDetailDto {
    Long id;
    String name;
    Double price;
    String mainPic;
    String description;
    List<String> pics;
    List<InventoryDto> inventories;
    Boolean like;
    String color;

}
