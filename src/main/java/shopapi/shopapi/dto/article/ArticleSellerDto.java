package shopapi.shopapi.dto.article;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticleSellerDto {
    Long id;
    Long productId;
    String mainPic;
    String category;
    List<String> inventories;
    Double price;
    Integer discount;
    String color;
    String name;
}
