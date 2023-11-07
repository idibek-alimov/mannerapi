package shopapi.shopapi.dto.category;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryUpdateDto extends CategoryCreateDto{
    private Long id;
}
