package shopapi.shopapi.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import shopapi.shopapi.models.user.Role;


public interface RoleRepository extends JpaRepository<Role,Long> {
    public Role findByName(String name);
}
