package shopapi.shopapi.models.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Picture {
    @Id
    @GeneratedValue(generator = "picture_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "picture_id_generator",initialValue = 1, allocationSize = 1,sequenceName = "Picture_id_generator")
    private Long id;

    private String name;
    private Boolean main = false;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;
}
