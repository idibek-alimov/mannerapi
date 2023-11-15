package shopapi.shopapi.repository.user;

import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import shopapi.shopapi.models.user.Role;


@Observed
public interface RoleRepository extends JpaRepository<Role,Long> {
    public Role findByName(String name);
}
