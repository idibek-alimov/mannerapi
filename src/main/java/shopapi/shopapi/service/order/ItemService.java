package shopapi.shopapi.service.order;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopapi.shopapi.dto.item.ItemCreateDto;
import shopapi.shopapi.models.order.Item;
import shopapi.shopapi.models.order.Order;
import shopapi.shopapi.models.user.User;
import shopapi.shopapi.repository.order.ItemRepository;
import shopapi.shopapi.service.product.InventoryService;
import shopapi.shopapi.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final InventoryService inventoryService;

    public void deleteTheThings(){
        itemRepository.deleteItems();
    }
    public void createItems(Order order, List<ItemCreateDto> items){
        for(ItemCreateDto item:items){
            this.createItem(order,item);
        }
    }
    public List<Item> getItemsByStatus(Integer status){
        return itemRepository.getByStatus(status);
    }
    public List<Item> getCustomerShippigItems(){
        User user = userService.getCurrentUser();
        if(user == null)
            return new ArrayList<>();
        return itemRepository.getCustomerShippingItems(user.getId());
    }
    public void dropTableItems(){
        itemRepository.dropTableItems();
    }
    public void changeStatus(Long itemId,Integer status){
        itemRepository.changeStatus(itemId,status);
    }
    public void setStatusDelivered(Long id){
        Item item = itemRepository.findById(id).get();
        if(item != null){
            item.setStatus(Item.Status.Delivered);
            itemRepository.save(item);
        }
    }
    public List<Item> getItemsByStatusAndUser(Integer status){
        User user = userService.getCurrentUser();
        if(user == null)
            return new ArrayList<>();
        return itemRepository.getByStatusAndUser(status,user.getId());
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
