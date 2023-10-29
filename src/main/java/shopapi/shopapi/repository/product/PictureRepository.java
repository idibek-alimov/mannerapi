package shopapi.shopapi.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shopapi.shopapi.models.product.Picture;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture,Long> {
    @Query(value = "SELECT name FROM picture WHERE article_id=?1 AND main=True",nativeQuery = true)
    public String getMainPic(Long id);

    @Query(value = "SELECT name FROM picture WHERE article_id=?1 AND (main!=True OR main=null)",nativeQuery = true)
    public List<String> getPics(Long id);

}
