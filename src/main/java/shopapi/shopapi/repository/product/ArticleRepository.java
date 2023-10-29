package shopapi.shopapi.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shopapi.shopapi.models.product.Article;
import shopapi.shopapi.models.product.Inventory;
import shopapi.shopapi.models.product.Like;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    @Query(value = "SELECT * FROM article WHERE product_id IN (SELECT id" +
            " FROM product product WHERE ?1 % ANY(STRING_TO_ARRAY(product.name,' ')) or" +
            " ?1 % ANY(STRING_TO_ARRAY(product.description,' ')))", nativeQuery = true)
    List<Article> searchArticles(String text);

    @Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM liked WHERE article_id=?2 and user_id=?1) THEN 'TRUE' ELSE 'FALSE' END",nativeQuery = true)
    Boolean findLikedByUserAndArticle(Long userId, Long articleId);
    @Query(value = "SELECT * FROM article WHERE id IN (SELECT article_id FROM liked WHERE user_id=?1)",nativeQuery = true)
    List<Article> findLikedByUser(Long id);

    @Query(value = "SELECT * FROM article WHERE product_id IN (SELECT product_id FROM article WHERE id=?1)",nativeQuery = true)
    List<Article> findOtherColors(Long id);

    @Query(value = "select inventory.* from inventory" +
            " inner join item ON item.inventory_id = inventory.id " +
            "where item.customer_id=?1 and item.status=0",nativeQuery = true)
    List<Inventory> findShippingInventories(Long userId);
    @Query(value = "select distinct inventory.* from inventory" +
            " inner join item ON item.inventory_id = inventory.id " +
            "where item.customer_id=?1 and item.status=1",nativeQuery = true)
    List<Inventory> findDeliveredInventories(Long userId);

}
