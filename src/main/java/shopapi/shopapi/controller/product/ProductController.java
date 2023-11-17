package shopapi.shopapi.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import shopapi.shopapi.dto.product.ProductCreateDto;
import shopapi.shopapi.dto.product.ProductDto;
import shopapi.shopapi.dto.product.ProductUpdateDto;
import shopapi.shopapi.dto.product.SellerProductDto;
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
        return productService.createProduct(product);
    }
    @PostMapping(path="/seller/update",consumes = {MediaType.APPLICATION_JSON_VALUE})//,consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin("*")
    public Long updateProduct(@RequestBody ProductUpdateDto product){
        return productService.updateProduct(product);
    }
    @GetMapping("/by/user")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<ProductDto> getProductsByUser(){
        return productService.getProductsByUser();
    }
    @GetMapping("/seller/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public SellerProductDto getProductById(@PathVariable("id")Long id){
        return productService.getProduct(id);
    }


}
