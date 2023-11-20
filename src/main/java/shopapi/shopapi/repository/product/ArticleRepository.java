package shopapi.shopapi.repository.product;

//import io.micrometer.observation.annotation.Observed;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import shopapi.shopapi.models.product.Article;
import shopapi.shopapi.models.product.Inventory;
import shopapi.shopapi.models.product.Like;

import java.util.List;
import java.util.Optional;

//@Observed
public interface ArticleRepository extends JpaRepository<Article,Long> {
        ///////////////////////////////////////////////////////////////
    //////////////////////// BASIC GET AND FIND /////////////////////////
    /////////////////////////////////////////////////////////////////////
    @Query(value = "SELECT article.* FROM article JOIN product ON " +
            "product.id = article.product_id WHERE product.user_id=?1 AND article.available=true",nativeQuery = true)
    List<Article> getByUserAvailable(Long userId);
    @Query(value = "SELECT article.* FROM article JOIN product ON " +
            "product.id = article.product_id WHERE product.user_id=?1 AND (article.available=false OR article.available IS NULL)",nativeQuery = true)
    List<Article> getByUserNotAvailable(Long userId);

    @Query(value = "SELECT * FROM article WHERE id=?1 AND available IS TRUE AND active IS TRUE",nativeQuery = true)
    Optional<Article> findById(Long id);

    @Query(value = "SELECT * FROM article " +
            "WHERE active=false OR active IS NULL " +
            "ORDER BY created_at DESC",nativeQuery = true)
    List<Article> getArticlesNotActive();
    @Query(value = "SELECT * FROM article " +
            "WHERE product_id=?1",nativeQuery = true)
    List<Article> getArticlesByProductId(Long id);



    @Query(value = "SELECT * FROM article WHERE product_id IN (SELECT id" +
            " FROM product product WHERE ?1 % ANY(STRING_TO_ARRAY(product.name,' ')) or" +
            " ?1 % ANY(STRING_TO_ARRAY(product.description,' '))) AND available IS TRUE AND active IS TRUE", nativeQuery = true)
    List<Article> searchArticles(String text,PageRequest pageRequest);

    @Query(value = "SELECT article.* FROM article JOIN product ON " +
            "product.id = article.product_id WHERE product.category_id=?1 AND article.available IS TRUE AND article.active IS TRUE ",nativeQuery = true)
    List<Article> getByCategory(Long id,PageRequest pageRequest);

    @Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM liked WHERE article_id=?2 and user_id=?1) THEN 'TRUE' ELSE 'FALSE' END",nativeQuery = true)
    Boolean findLikedByUserAndArticle(Long userId, Long articleId);
    @Query(value = "SELECT * FROM article WHERE id IN (SELECT article_id FROM liked WHERE user_id=?1) AND available IS TRUE AND active IS TRUE",nativeQuery = true)
    List<Article> findLikedByUser(Long id);

    @Query(value = "SELECT * FROM article WHERE product_id IN (SELECT product_id FROM article WHERE id=?1) AND available IS TRUE AND active IS TRUE ",nativeQuery = true)
    List<Article> findOtherColors(Long id);

    @Query(value = "select inventory.* from inventory" +
            " inner join item ON item.inventory_id = inventory.id " +
            "where item.customer_id=?1 and item.status=0",nativeQuery = true)
    List<Inventory> findShippingInventories(Long userId);
    @Query(value = "select distinct inventory.* from inventory" +
            " inner join item ON item.inventory_id = inventory.id " +
            "where item.customer_id=?1 and item.status=1",nativeQuery = true)
    List<Inventory> findDeliveredInventories(Long userId);

    @Query(value = "SELECT * FROM article WHERE available IS TRUE and active IS TRUE",nativeQuery = true)
    List<Article> findAll(PageRequest pageRequest);

    /////////////////////////////////////////////////////////////
    /////////////////// SET UPDATE AND STUFF ////////////////
    //////////////////////////////////////////////////////////
    @Modifying
    @Query(value = "UPDATE article SET active=true WHERE id=?1",nativeQuery = true)
    void setActive(Long articleId);

    @Modifying
    @Query(value = "UPDATE article SET active=false WHERE id=?1",nativeQuery = true)
    void deactivateArticle(Long articleId);


    @Modifying
    @Query(value = "UPDATE article SET available=true WHERE id IN " +
            "(SELECT article.id FROM article JOIN product ON " +
            "article.product_id=product.id WHERE product.user_id=?2 " +
            "AND article.id=?1)",nativeQuery = true)
    void setAvailable(Long id,Long userId);
    @Modifying
    @Query(value = "UPDATE article SET available=false WHERE id IN " +
            "(SELECT article.id FROM article JOIN product ON " +
            "article.product_id=product.id WHERE product.user_id=?2 " +
            "AND article.id=?1)",nativeQuery = true)
    void setUnavailable(Long id,Long userId);

}
