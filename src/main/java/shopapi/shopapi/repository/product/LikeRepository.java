package shopapi.shopapi.repository.product;

//import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import shopapi.shopapi.models.product.Like;

import java.util.List;
import java.util.Optional;

//@Observed
public interface LikeRepository extends JpaRepository<Like,Long> {
    @Query(value = "SELECT * FROM liked WHERE user_id=?1 AND article_id=?2",nativeQuery = true)
    Optional<Like> findByUserAndArticle(Long userId, Long articleId);
    @Modifying
    @Query(value = "DELETE FROM liked WHERE user_id=?1 AND article_id=?2",nativeQuery = true)
    void deleteByUserAndArticle(Long userId, Long articleId);

    @Query(value = "SELECT picture.name FROM picture INNER JOIN liked ON liked.article_id = picture.article_id WHERE liked.user_id=?1 AND picture.main=true",nativeQuery = true)
    List<String> findPicLikedByUser(Long id);


    @Modifying
    @Query(value = "CREATE EXTENSION pg_trgm",nativeQuery = true)
    void createExtension();
}
