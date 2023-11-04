package shopapi.shopapi.service.order;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopapi.shopapi.dto.order.OrderCreateDto;
import shopapi.shopapi.models.order.Order;
import shopapi.shopapi.models.user.Address;
import shopapi.shopapi.models.user.User;
import shopapi.shopapi.repository.order.OrderRepository;
import shopapi.shopapi.service.user.AddressService;
import shopapi.shopapi.service.user.UserService;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final UserService userService;
    private final AddressService addressService;

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
}
