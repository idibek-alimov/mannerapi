package shopapi.shopapi.service.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopapi.shopapi.dto.category.CategoryCreateDto;
import shopapi.shopapi.dto.category.CategoryDto;
import shopapi.shopapi.dto.category.CategoryUpdateDto;
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
    public void updateCategory(CategoryUpdateDto categoryUpdateDto){
        Category category = this.updateDtoToCategory(categoryUpdateDto);
        System.out.println(category);
        categoryRepository.save(category);
    }

    public List<CategoryDto> getParentCategories(){
        return categoryRepository.getParentCategories().stream().map(this::categoryToDto).collect(Collectors.toList());
    }
    public List<CategoryDto> getCategoriesByParent(Long id){
        return categoryRepository.getCategoryByParent(id).stream().map(this::categoryToDto).collect(Collectors.toList());
    }

    public List<CategoryDto> getCategories(){
        return categoryRepository.findAll().stream().map(this::categoryToDto).collect(Collectors.toList());
    }
    private CategoryDto categoryToDto(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .parent(category.getCategory() != null ? category.getCategory().getId() : null)
                .description(category.getDescription())
                .build();
    }

    private Category createDtoToCategory(CategoryCreateDto categoryCreateDto){
        Category parentCategory = this.getCategoryById(categoryCreateDto.getParent());
        return Category.builder()
                .name(categoryCreateDto.getName())
                .category(parentCategory)
                .description(categoryCreateDto.getDescription())
                .build();
    }
    private Category updateDtoToCategory(CategoryUpdateDto categoryUpdateDto){
        Category category = this.createDtoToCategory(categoryUpdateDto);
       category.setId(categoryUpdateDto.getId());
       return category;
    }
}
