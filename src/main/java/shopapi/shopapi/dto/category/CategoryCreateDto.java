package shopapi.shopapi.dto.category;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryCreateDto {
    private String name;
}