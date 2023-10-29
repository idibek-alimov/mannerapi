package shopapi.shopapi.service.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopapi.shopapi.dto.product.ProductCreateDto;
import shopapi.shopapi.models.product.Product;
import shopapi.shopapi.repository.product.ProductRepository;
import shopapi.shopapi.repository.user.UserRepository;
import shopapi.shopapi.service.user.UserService;

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
    public Product productCreateDtoToProduct(ProductCreateDto productCreateDto){
        return Product.builder()
                .name(productCreateDto.getName())
                .description(productCreateDto.getDescription())
                .category(categoryService.getCategoryById(productCreateDto.getCategory()))
                .user(userService.getCurrentUser())
                .build();
    }
}
