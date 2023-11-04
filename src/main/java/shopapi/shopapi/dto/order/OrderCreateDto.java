package shopapi.shopapi.dto.order;

import lombok.*;
import shopapi.shopapi.dto.address.AddressDto;
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
    AddressDto address;
    String extraInfo;
    List<ItemCreateDto> items;
}
