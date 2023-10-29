package shopapi.shopapi.service.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import shopapi.shopapi.models.product.Article;
import shopapi.shopapi.models.product.Like;
import shopapi.shopapi.models.user.User;
import shopapi.shopapi.repository.product.LikeRepository;
import shopapi.shopapi.service.user.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final ArticleService articleService;

    public void likeArticle(Long id){
        Article article = articleService.getActualArticleById(id);
        User user = userService.getCurrentUser();
        if(user!=null && article!=null){
            if(checkLike(article.getId(),user.getId())){
                likeRepository.deleteByUserAndArticle(user.getId(),article.getId());
            }
            else {
                likeRepository.save(Like.builder().article(article).user(user).build());
            }
        }
    }
    public List<String> findPicLiked(){
        User user = userService.getCurrentUser();
        if(user == null)
            return null;
        return likeRepository.findPicLikedByUser(userService.getCurrentUser().getId());
    }
    public boolean checkLike(Long articleId,Long userId){
        return likeRepository.findByUserAndArticle(userId,articleId).isPresent();
    }
    public void createExtension(){
        likeRepository.createExtension();
    }

}
