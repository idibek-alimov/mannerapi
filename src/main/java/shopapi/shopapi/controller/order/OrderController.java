package shopapi.shopapi.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import shopapi.shopapi.dto.inventory.InventoryCreateDto;
import shopapi.shopapi.dto.item.ItemCreateDto;
import shopapi.shopapi.dto.item.ItemDto;
import shopapi.shopapi.dto.order.OrderCreateDto;
import shopapi.shopapi.models.order.Item;
import shopapi.shopapi.service.order.OrderService;

import java.util.List;

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
    @GetMapping("/items/{status}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<ItemDto> getUncheckedItems(@PathVariable("status")Integer status){
        return orderService.getItemsByStatus(status);
    }
    @GetMapping("/all/delete")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public void deleteAll(){
        orderService.deleteTheThing();
    }
    @GetMapping("/item/change/status/{id}/{status}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public void changeStatus(@PathVariable("id")Long id,@PathVariable("status")Integer status){
        orderService.changeStatus(id,status);
    }
}
