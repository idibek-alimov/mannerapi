package shopapi.shopapi.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import shopapi.shopapi.models.user.User;

public interface UserRepository extends JpaRepository<User,Long> {
    public User findByUsername(String username);
}
