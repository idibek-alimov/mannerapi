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
    String mainPic;
    String category;
    List<String> inventories;
    Double price;
    String color;
    String name;
}
