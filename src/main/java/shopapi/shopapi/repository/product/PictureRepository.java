package shopapi.shopapi.repository.product;

//import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import shopapi.shopapi.models.product.Picture;

import java.util.List;
import java.util.Optional;

//@Observed
public interface PictureRepository extends JpaRepository<Picture,Long> {
    @Query(value = "SELECT name FROM picture WHERE article_id=?1 AND main=True",nativeQuery = true)
    public String getMainPic(Long id);

    @Query(value = "SELECT name FROM picture WHERE article_id=?1 AND (main!=True OR main=null)",nativeQuery = true)
    public List<String> getPics(Long id);
    @Modifying
    @Query(value = "DELETE FROM picture WHERE article_id=?1 AND main IS TRUE",nativeQuery = true)
    void deleteMainPicture(Long id);

    @Modifying
    @Query(value = "DELETE FROM picture WHERE id=?1",nativeQuery = true)
    void deleteById(Long id);
    @Modifying
    @Query(value = "DELETE FROM picture WHERE id NOT IN ?1",nativeQuery = true)
    void deleteNotIncluding(List<Long> ids);

}
