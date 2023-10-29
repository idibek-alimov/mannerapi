package shopapi.shopapi.dto.inventory;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InventoryDto {
    Long id;
    Boolean available;
    String size;
}
