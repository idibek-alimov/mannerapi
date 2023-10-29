package shopapi.shopapi.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shopapi.shopapi.models.user.User;

public interface UserRepository extends JpaRepository<User,Long> {
    public User findByUsername(String username);

    @Query(value = "SELECT CASE WHEN EXISTS (SELECT from user_list " +
            "where username=?1) THEN 'TRUE' ELSE 'FALSE' END")
    Boolean userNameAvailable(String username);
}
