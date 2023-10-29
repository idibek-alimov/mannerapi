package shopapi.shopapi.models.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {
    @Id
    @GeneratedValue(generator = "article_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "article_id_generator",initialValue = 1, allocationSize = 1,sequenceName = "Article_id_generator")
    private Long id;

    @ManyToOne
    private Color color;

    @JsonBackReference
    @ManyToOne
    private Product product;
    private Double price;

    @JsonManagedReference
    @Column(name = "inventories")
    @OneToMany(
            mappedBy = "article",
            cascade = CascadeType.ALL
            //orphanRemoval = true
    )
    private List<Inventory> inventory = new ArrayList<>();

    @JsonManagedReference
    @Column(name="pictures")
    @OneToMany(
            mappedBy = "article",
            cascade = CascadeType.ALL
//            orphanRemoval = true
    )
    private List<Picture> pictures = new ArrayList<>();


    private Date createdAt;
    @Column
    @PrePersist
    void createdAt(){
        this.createdAt = new Date();
    }
}
