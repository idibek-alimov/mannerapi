package shopapi.shopapi.models.order;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import shopapi.shopapi.models.user.Address;
import shopapi.shopapi.models.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {


    public  enum Status {Queue,Shipping,Delivered};

    @Id
    @GeneratedValue(generator = "orders_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "orders_id_generator", sequenceName = "Orders_id_generator",allocationSize=1)
    private Long id;

    private Status status = Status.Queue;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    @JsonManagedReference
    @Column(name="items")
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Item> items = new ArrayList<>();

    @ManyToOne
    private Address address;

    @Column(columnDefinition = "TEXT")
    private String extraInfo;

    @CreationTimestamp
    @CreatedDate
    private LocalDateTime created_at;
    @UpdateTimestamp
    private LocalDateTime updated_at;
    public Order(User user){
        this.user = user;
    }

    @PrePersist
    public void setCreationTime(){
        this.created_at = LocalDateTime.now();
    }
//    public static DeliveryMethod getDeliveryMethodByIndex(Integer index){
//        if (index == 0){
//            return DeliveryMethod.PickPoint;
//        }
//        else if(index == 1){
//            return  DeliveryMethod.Courier;
//        }
//        else {
//            return null;
//        }
//    }
}