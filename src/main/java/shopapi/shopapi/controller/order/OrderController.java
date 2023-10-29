package shopapi.shopapi.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import shopapi.shopapi.dto.inventory.InventoryCreateDto;
import shopapi.shopapi.dto.item.ItemCreateDto;
import shopapi.shopapi.dto.order.OrderCreateDto;
import shopapi.shopapi.service.order.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;
    @PostMapping(path="/create",consumes = {MediaType.APPLICATION_JSON_VALUE})//,MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin("*")
    public void createOrder(@RequestBody OrderCreateDto orderCreateDto){
//        System.out.println("in order create");
//        for(ItemCreateDto item:orderCreateDto.getItems()){
//            System.out.println("inventory_id="+item.getInventory()+"  quantity="+item.getQuantity());
//        }
        orderService.createOrder(orderCreateDto);
    }
}
