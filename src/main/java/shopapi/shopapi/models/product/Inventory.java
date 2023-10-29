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
public class Inventory {
    @Id
    @GeneratedValue(generator = "inventory_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "inventory_id_generator",initialValue = 1, allocationSize = 1,sequenceName = "Inventory_id_generator")
    private Long id;
    @JsonBackReference
    @ManyToOne
    private Article article;
    private Integer quantity = 0;
    private Boolean available = true;
    private String size;

}
