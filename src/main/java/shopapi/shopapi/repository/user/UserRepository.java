package shopapi.shopapi.repository.user;

//import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shopapi.shopapi.models.user.User;

//@Observed
public interface UserRepository extends JpaRepository<User,Long> {
    public User findByUsername(String username);

    @Query(value = "SELECT CASE WHEN EXISTS (SELECT FROM user_list " +
            "WHERE username=?1) THEN 'TRUE' ELSE 'FALSE' END",nativeQuery = true)
    Boolean userNameAvailable(String username);
}
