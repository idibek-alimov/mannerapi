package shopapi.shopapi.dto.article;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticleColorDto {
    Long id;
    String mainPic;
}
