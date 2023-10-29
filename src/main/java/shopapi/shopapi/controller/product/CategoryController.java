package shopapi.shopapi.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import shopapi.shopapi.dto.category.CategoryCreateDto;
import shopapi.shopapi.dto.category.CategoryDto;
import shopapi.shopapi.service.product.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<CategoryDto> getCategories(){
        return categoryService.getCategories();
    }
    @PostMapping(path="/master/create",consumes = {MediaType.APPLICATION_JSON_VALUE})//,consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin("*")
    public void createCategory(@RequestBody CategoryCreateDto category){
        categoryService.createCategory(category);
    }


}
