package shopapi.shopapi.service.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shopapi.shopapi.dto.article.*;
import shopapi.shopapi.models.product.Article;
import shopapi.shopapi.models.product.Inventory;
import shopapi.shopapi.models.product.Picture;
import shopapi.shopapi.models.user.User;
import shopapi.shopapi.repository.product.ArticleRepository;
import shopapi.shopapi.service.user.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {
    private final ColorService colorService;
    private final ProductService productService;
    private final ArticleRepository articleRepository;
    private final InventoryService inventoryService;
    private final PictureService pictureService;
    private final UserService userService;

    public Article createArticle(ArticleCreateDto article){
        Article newArticle = articleRepository.save(createDtoToArticle(article));
        inventoryService.createInventories(article.getInventories(),newArticle);
        return newArticle;
    }
    public List<ArticleOrderedDto> getShippingArticles(){
        User user = userService.getCurrentUser();
        if(user == null)
            return null;
        return inventoryService.getShippingInventories(user.getId()).stream().map(this::inventoryToArticleOrderDto).collect(Collectors.toList());
    }
    public List<ArticleOrderedDto> getDeliveredArticles(){
        User user = userService.getCurrentUser();
        if(user == null)
            return null;

        return inventoryService.getDeliveredInventories(user.getId()).stream().map(this::inventoryToArticleOrderDto).collect(Collectors.toList());
    }
    public List<ArticleDto> findLikedArticles(){
        User user = userService.getCurrentUser();
        if(user==null)
            return null;
        return articleRepository.findLikedByUser(user.getId()).stream().map(this::articleToDto).collect(Collectors.toList());
    }
    public List<ArticleDto> getArticles(){
        return articleRepository.findAll().stream().map(this::articleToDto).collect(Collectors.toList());
    }
    public List<ArticleDto> searchByName(String text){
        return articleRepository.searchArticles(text).stream().map(this::articleToDto).collect(Collectors.toList());
    }
    public ArticleDetailDto getArticleById(Long id){
        Optional<Article> optionalArticle = articleRepository.findById(id);
        return optionalArticle.map(this::articleToDetailDto).orElse(null);
    }
    public Article getActualArticleById(Long id){
        return articleRepository.findById(id).orElse(null);
    }
    public void createArticleWithPictures(ArticleCreateDto articleDto,List<MultipartFile> files,MultipartFile mainPic){
        Article article = createArticle(articleDto);
        pictureService.createPictures(files,article);
        pictureService.createMainPic(mainPic,article);

    }
    public List<ArticleDto> getByCategory(Long id){
        return articleRepository.getByCategory(id).stream().map(this::articleToDto).collect(Collectors.toList());
    }
    public List<ArticleColorDto> getOtherColors(Long id){
        return articleRepository.findOtherColors(id).stream().map(this::articleToArticleColorDto).collect(Collectors.toList());
    }
    private Article createDtoToArticle(ArticleCreateDto article){
        return Article.builder()
                .color(colorService.getColorById(article.getColor()))
                .product(productService.getProductById(article.getProduct()))
                .price(article.getPrice())
                .build();
    }
    private ArticleDto articleToDto(Article article){
        return ArticleDto.builder()
                .id(article.getId())
                .name(article.getProduct().getName())
                .price(article.getPrice())
                .pic(pictureService.getMainPic(article.getId()))
                .build();
    }
    private ArticleDetailDto articleToDetailDto(Article article){
//        System.out.println("user id ="+userService.getCurrentUser().getId());
//        System.out.println("article id="+article.getId());
//        System.out.println("likes->"+(articleRepository.findLikedByUserAndArticle(userService.getCurrentUser().getId(), article.getId())));
        return ArticleDetailDto.builder()
                .name(article.getProduct().getName())
                .id(article.getId())
                .description(article.getProduct().getDescription())
                .mainPic(pictureService.getMainPic(article.getId()))
                .pics(pictureService.getPics(article.getId()))
                .price(article.getPrice())
                .color(article.getColor().getName())
                .inventories(inventoryService.getInventoryDTOsByArticle(article.getId()))
                .like(userService.getCurrentUser() != null && articleRepository.findLikedByUserAndArticle(userService.getCurrentUser().getId(), article.getId()))
                .build();
    }
    private ArticleColorDto articleToArticleColorDto(Article article){
       return ArticleColorDto.builder()
                .id(article.getId())
                .mainPic(pictureService.getMainPic(article.getId()))
                .build();
    }
    private ArticleOrderedDto inventoryToArticleOrderDto(Inventory inventory){
        Article article = inventory.getArticle();
        return ArticleOrderedDto.builder()
                .id(article.getId())
                .size(inventory.getSize())
                .name(article.getProduct().getName())
                .price(article.getPrice())
                .pic(pictureService.getMainPic(article.getId()))
                .build();

    }

}
