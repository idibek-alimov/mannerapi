package shopapi.shopapi.dto.inventory;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InventoryUpdateDto {
    Long id;
    private String size;
}
