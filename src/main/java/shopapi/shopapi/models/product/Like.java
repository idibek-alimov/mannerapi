package shopapi.shopapi.models.product;

import jakarta.persistence.*;
import lombok.*;
import shopapi.shopapi.models.user.User;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="liked")
public class Like {
    @Id
    @GeneratedValue(generator = "like_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "like_id_generator",initialValue = 1, allocationSize = 1,sequenceName = "Like_id_generator")
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Article article;
    @Column
    private Date createdAt;

    @Column
    @PrePersist
    void createdAt(){
        this.createdAt = new Date();
    }
}
