package shopapi.shopapi.service.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopapi.shopapi.dto.product.ProductCreateDto;
import shopapi.shopapi.dto.product.ProductDto;
import shopapi.shopapi.dto.product.ProductUpdateDto;
import shopapi.shopapi.models.product.Product;
import shopapi.shopapi.models.user.User;
import shopapi.shopapi.repository.product.ProductRepository;
import shopapi.shopapi.repository.user.UserRepository;
import shopapi.shopapi.service.user.UserService;

import java.util.List;
import java.util.Optional;
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
    public Long updateProduct(ProductUpdateDto productUpdateDto){
        Optional<Product> optionalProduct = productRepository.findById(productUpdateDto.getId());
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setName(productUpdateDto.getName());
            product.setDescription(productUpdateDto.getDescription());
            product = productRepository.save(product);
            return  product.getId();
        }
        return null;
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
    public ProductDto getProduct(Long id){
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.map(this::productToDto).orElse(null);
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
