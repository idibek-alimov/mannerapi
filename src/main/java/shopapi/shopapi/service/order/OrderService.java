package shopapi.shopapi.service.order;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopapi.shopapi.dto.order.OrderCreateDto;
import shopapi.shopapi.models.order.Order;
import shopapi.shopapi.repository.order.OrderRepository;
import shopapi.shopapi.service.user.UserService;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final UserService userService;

    public void createOrder(OrderCreateDto orderDto){
        Order order = orderRepository.save(Order.builder()
                        .address(orderDto.getAddress())
                        .user(userService.getCurrentUser())
                        .status(Order.Status.Shipping)
                .build());
        itemService.createItems(order,orderDto.getItems());
    }
}
