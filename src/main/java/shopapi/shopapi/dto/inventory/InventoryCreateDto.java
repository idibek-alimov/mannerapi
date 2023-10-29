package shopapi.shopapi.dto.inventory;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InventoryCreateDto {
    private String size;
}
