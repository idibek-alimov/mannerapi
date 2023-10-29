package shopapi.shopapi.service.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopapi.shopapi.dto.product.ProductCreateDto;
import shopapi.shopapi.dto.product.ProductDto;
import shopapi.shopapi.models.product.Product;
import shopapi.shopapi.models.user.User;
import shopapi.shopapi.repository.product.ProductRepository;
import shopapi.shopapi.repository.user.UserRepository;
import shopapi.shopapi.service.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductRepository productRepository;
    public Long createProduct(ProductCreateDto productCreateDto){
        return productRepository.save(productCreateDtoToProduct(productCreateDto)).getId();
    }
    public Product getProductById(Long id){
        return productRepository.findById(id).get();
    }

    public List<ProductDto> getProductsByUser(){
        User user = userService.getCurrentUser();
        if(user == null)
            return null;
        return productRepository.findByUserId(user.getId()).stream().map(this::productToDto).collect(Collectors.toList());
    }

    public ProductDto productToDto(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory().getId())
                .build();
    }
    public Product productCreateDtoToProduct(ProductCreateDto productCreateDto){
        return Product.builder()
                .name(productCreateDto.getName())
                .description(productCreateDto.getDescription())
                .category(categoryService.getCategoryById(productCreateDto.getCategory()))
                .user(userService.getCurrentUser())
                .build();
    }
}
