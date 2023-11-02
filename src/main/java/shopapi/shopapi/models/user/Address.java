package shopapi.shopapi.models.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(generator = "address_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "address_id_generator", sequenceName = "Address_id_generator",allocationSize=1)
    private Long id;
    private String latitude;
    private String longitude;
    @ManyToOne
    private User user;

    private Date createdAt;
    private String addressLine;
    @Column
    @PrePersist
    void createdAt(){
        this.createdAt = new Date();
    }
}
