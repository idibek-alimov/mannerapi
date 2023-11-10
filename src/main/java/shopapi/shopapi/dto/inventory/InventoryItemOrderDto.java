package shopapi.shopapi.dto.inventory;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.ManyToOne;
import lombok.*;
import shopapi.shopapi.models.product.Article;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InventoryItemOrderDto{
    private Long id;
    @JsonBackReference
    @ManyToOne
    private Article article;
    private Integer quantity;
    private Boolean available;
    private String size;
    String addressLine;
    String latitude;
    String longitude;
    @JsonProperty("item_quantity")
    Integer itemQuantity;
}
