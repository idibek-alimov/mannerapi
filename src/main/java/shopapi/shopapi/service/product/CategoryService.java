package shopapi.shopapi.service.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopapi.shopapi.dto.category.CategoryCreateDto;
import shopapi.shopapi.dto.category.CategoryDto;
import shopapi.shopapi.models.product.Category;
import shopapi.shopapi.repository.product.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public Category getCategoryById(Long id){
        if(id!=null)
            return categoryRepository.findById(id).orElse(null);
        return null;
    }
    public void createCategory(CategoryCreateDto categoryCreateDto){
        categoryRepository.save(createDtoToCategory(categoryCreateDto));
    }
    public List<CategoryDto> getParentCategories(){
        return categoryRepository.getParentCategories().stream().map(this::categoryToDto).collect(Collectors.toList());
    }
    public List<CategoryDto> getCategoriesByParent(Long id){
        return categoryRepository.getCategoryByParent(id).stream().map(this::categoryToDto).collect(Collectors.toList());
    }

    public List<CategoryDto> getCategories(){
        return categoryRepository.findAll().stream().map(category -> categoryToDto(category)).collect(Collectors.toList());
    }
    private CategoryDto categoryToDto(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .parent(category.getCategory().getId())
                .build();
    }
    private Category createDtoToCategory(CategoryCreateDto categoryCreateDto){
        Category parentCategory = this.getCategoryById(categoryCreateDto.getParent());
        return Category.builder()
                .name(categoryCreateDto.getName())
                .category(parentCategory)
                .build();
    }
}
