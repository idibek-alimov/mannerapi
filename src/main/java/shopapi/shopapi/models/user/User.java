package shopapi.shopapi.models.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import shopapi.shopapi.models.product.Inventory;

@Table(name="user_list")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails{
    @Id
    @GeneratedValue(generator = "user_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_id_generator", sequenceName = "User_id_generator",allocationSize=1)
    @Column(unique=true, nullable=false)
    private Long id;

    private String username;
    private String password;
    private String name;
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> { return new SimpleGrantedAuthority(role.getName());}).collect(Collectors.toList());
    }

    @JsonManagedReference
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
            //orphanRemoval = true
    )
    private List<Address> addresses = new ArrayList<>();

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
