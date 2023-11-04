package shopapi.shopapi.dto.item;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDto {
    private Long articleId;
    private String size;
    private String mainPic;
    private String name;
    private Double price;
    private String addressLine;
    private String extraInfo;
    private Integer quantity;
}
