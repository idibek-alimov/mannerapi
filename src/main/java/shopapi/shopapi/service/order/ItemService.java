package shopapi.shopapi.service.order;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopapi.shopapi.dto.item.ItemCreateDto;
import shopapi.shopapi.models.order.Item;
import shopapi.shopapi.models.order.Order;
import shopapi.shopapi.repository.order.ItemRepository;
import shopapi.shopapi.service.product.InventoryService;
import shopapi.shopapi.service.user.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final InventoryService inventoryService;
    public void createItems(Order order, List<ItemCreateDto> items){
        for(ItemCreateDto item:items){
            this.createItem(order,item);
        }
    }
    public List<Item> getItemsByStatus(Integer status){
        return itemRepository.getByStatus(status);
    }
    public void createItem(Order order, ItemCreateDto item){
        itemRepository.save(Item.builder()
                        .customer(userService.getCurrentUser())
                        .quantity(item.getQuantity())
                        .inventory(inventoryService.getById(item.getInventory()))
                        .order(order)
                        .status(Item.Status.Queue)
                .build());
    }

}
