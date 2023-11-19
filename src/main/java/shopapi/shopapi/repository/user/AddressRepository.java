package shopapi.shopapi.repository.user;

//import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import shopapi.shopapi.models.user.Address;

import java.util.List;
import java.util.Optional;

//@Observed
public interface AddressRepository extends JpaRepository<Address,Long> {
    @Query(value = "SELECT * FROM address WHERE user_id=?1",nativeQuery = true)
    List<Address> findByUserId(Long id);
    @Query(value = "SELECT * FROM address WHERE user_id=?1 AND id=?2",nativeQuery = true)
    Address findByUserIdAndAddressId(Long userId,Long addressId);

    @Modifying
    @Query(value = "DELETE FROM address",nativeQuery = true)
    void deleteAllAddress();
}
