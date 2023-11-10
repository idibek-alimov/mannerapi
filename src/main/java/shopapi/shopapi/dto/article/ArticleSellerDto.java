package shopapi.shopapi.dto.article;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticleSellerDto {
    Long id;
    String mainPic;
    Double price;
    String color;
    String name;
}
