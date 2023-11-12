package shopapi.shopapi.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import shopapi.shopapi.dto.product.ProductCreateDto;
import shopapi.shopapi.dto.product.ProductDto;
import shopapi.shopapi.models.order.Item;
import shopapi.shopapi.service.product.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;
    @PostMapping(path="/seller/create",consumes = {MediaType.APPLICATION_JSON_VALUE})//,consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin("*")
    public Long createProduct(@RequestBody ProductCreateDto product){
        System.out.println(product.getCategory());
        return productService.createProduct(product);
    }
    @GetMapping("/by/user")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<ProductDto> getProductsByUser(){
        return productService.getProductsByUser();
    }


}
