package shopapi.shopapi.models.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(generator = "role_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "role_id_generator", sequenceName = "Role_id_generator",allocationSize=1)
    private Long id;
    private String name;

    public Role(String name){
        this.name = name;
    }

}