package shopapi.shopapi.service.order;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopapi.shopapi.dto.item.ItemDto;
import shopapi.shopapi.dto.order.OrderCreateDto;
import shopapi.shopapi.models.order.Item;
import shopapi.shopapi.models.order.Order;
import shopapi.shopapi.models.product.Inventory;
import shopapi.shopapi.models.user.Address;
import shopapi.shopapi.models.user.User;
import shopapi.shopapi.repository.order.OrderRepository;
import shopapi.shopapi.service.product.PictureService;
import shopapi.shopapi.service.user.AddressService;
import shopapi.shopapi.service.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final UserService userService;
    private final AddressService addressService;
    private final PictureService pictureService;

    public void deleteTheThing(){
        itemService.deleteTheThings();
        orderRepository.deleteOrders();
    }
    public void changeStatus(Long itemId,Integer status){
        itemService.changeStatus(itemId,status);
    }
    public void dropTableItems(){
        itemService.dropTableItems();
    }
    public void setStatusDelivered(Long id){
        itemService.setStatusDelivered(id);
    }



    public void createOrder(OrderCreateDto orderDto){
        User user = this.userService.getCurrentUser();
        if(user == null)
            return;
        Address address = addressService.findOrCreateAddress(orderDto.getAddress(),user);
        Order order = orderRepository.save(Order.builder()
                        .address(address)
                        .user(userService.getCurrentUser())
                        .extraInfo(orderDto.getExtraInfo())
                        .status(Order.Status.Queue)
                .build());
        itemService.createItems(order,orderDto.getItems());
    }
    public List<ItemDto> getItemsByStatus(Integer status){
        return itemService.getItemsByStatus(status).stream().map(this::itemToDto).collect(Collectors.toList());
    }

    public ItemDto itemToDto(Item item){
        return ItemDto.builder()
                .articleId(item.getInventory().getArticle().getId())
                .name(item.getInventory().getArticle().getProduct().getName())
                .price(item.getInventory().getArticle().getPrice())
                .mainPic(pictureService.getMainPic(item.getInventory().getArticle()
                        .getId()))
                .addressLine(item.getOrder().getAddress().getAddressLine())
                .size(item.getInventory().getSize())
                .quantity(item.getQuantity())
                .extraInfo(item.getOrder().getExtraInfo())
                .build();
    }
}
