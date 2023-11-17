package shopapi.shopapi.dto.category;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryUpdateDto{
    private Long id;
    private Long parent;
    private String name;
    private String description;
}
