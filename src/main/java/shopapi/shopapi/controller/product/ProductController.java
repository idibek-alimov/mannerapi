package shopapi.shopapi.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import shopapi.shopapi.dto.product.ProductCreateDto;
import shopapi.shopapi.service.product.ProductService;

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
}
