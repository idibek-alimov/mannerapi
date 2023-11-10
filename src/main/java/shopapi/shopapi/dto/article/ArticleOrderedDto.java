package shopapi.shopapi.dto.article;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticleOrderedDto {
    Long id;
    String name;
    Double price;
    String pic;
    String size;
    String address;
    String latitude;
    String longitude;
    Integer quantity;
    Long itemId;
}
