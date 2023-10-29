package shopapi.shopapi.dto.picture;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PictureCreateDto {
    private String name;
}
