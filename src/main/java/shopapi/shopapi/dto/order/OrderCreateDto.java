package shopapi.shopapi.dto.order;

import lombok.*;
import shopapi.shopapi.dto.inventory.InventoryCreateDto;
import shopapi.shopapi.dto.item.ItemCreateDto;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderCreateDto {
    String address;
    List<ItemCreateDto> items;
}
