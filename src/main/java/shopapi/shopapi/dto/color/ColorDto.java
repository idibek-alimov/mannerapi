package shopapi.shopapi.dto.color;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ColorDto {
    private Long id;
    private String name;
}
