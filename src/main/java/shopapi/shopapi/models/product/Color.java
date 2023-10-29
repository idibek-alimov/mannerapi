package shopapi.shopapi.models.product;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Color {
    @Id
    @GeneratedValue(generator = "color_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "color_id_generator",initialValue = 1, allocationSize = 1,sequenceName = "Color_id_generator")
    private Long id;

    private String name;
}
