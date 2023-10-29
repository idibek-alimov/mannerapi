package shopapi.shopapi.dto.item;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemCreateDto {
    private Long inventory;
    private Integer quantity;
}
