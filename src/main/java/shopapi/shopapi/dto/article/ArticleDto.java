package shopapi.shopapi.dto.article;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticleDto {
    Long id;
    String name;
    Double price;
    String pic;
}
