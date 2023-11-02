package shopapi.shopapi.models.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import shopapi.shopapi.models.product.Inventory;
import shopapi.shopapi.models.user.User;

import java.time.LocalDateTime;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    //@JsonView(View.OnlyId.class)
    public  enum Status {Queue,Shipping,Delivered};
    @Id
    @GeneratedValue(generator = "items_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "items_id_generator", sequenceName = "Items_id_generator",allocationSize=1)
    private Long id;


    //@JsonView(View.OnlyId.class)
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    private Inventory inventory;
    //@JsonView(View.OnlyId.class)

    private Status status = Status.Queue;

    private Integer quantity;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order ;


    @CreationTimestamp
    @CreatedDate
    private LocalDateTime created_at;

//    @JsonBackReference
//    @ManyToOne(fetch = FetchType.LAZY)
//    private User owner;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private User customer;

//    public Item(Inventory inventory,Integer quantity,Order order,User owner){
//        this.inventory = inventory;
//        this.quantity = quantity;
//        this.order = order;
//        this.owner = owner;
//    }
//    public Item(Inventory inventory,Integer quantity,Order order,User owner,User customer){
//        this(inventory,quantity,order,owner);
//        this.customer = customer;
//    }
}
