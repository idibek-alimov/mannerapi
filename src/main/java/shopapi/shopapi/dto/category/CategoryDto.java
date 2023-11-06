package shopapi.shopapi.dto.category;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDto {
    private Long id;
    private String name;
    private Long parent;
}
