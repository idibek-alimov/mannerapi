package shopapi.shopapi.models.product;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import shopapi.shopapi.models.user.User;

import java.util.Date;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(generator = "product_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "product_id_generator",initialValue = 1, allocationSize = 1,sequenceName = "Product_id_generator")
    private Long id;
    @ManyToOne
    private Category category;

    private String name;

    @ManyToOne
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private User user;

    //@Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private Date createdAt;

    @Column
    @PrePersist
    void createdAt(){
        this.createdAt = new Date();
    }
}
