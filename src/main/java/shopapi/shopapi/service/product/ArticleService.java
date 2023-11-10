package shopapi.shopapi.service.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shopapi.shopapi.dto.article.*;
import shopapi.shopapi.dto.inventory.InventoryItemOrderDto;
import shopapi.shopapi.models.order.Item;
import shopapi.shopapi.models.product.Article;
import shopapi.shopapi.models.product.Inventory;
import shopapi.shopapi.models.product.Picture;
import shopapi.shopapi.models.user.Address;
import shopapi.shopapi.models.user.User;
import shopapi.shopapi.repository.product.ArticleRepository;
import shopapi.shopapi.service.order.ItemService;
import shopapi.shopapi.service.user.UserService;

import java.util.ArrayList;
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
    private final ItemService itemService;


    ///////////////CREATE////////////////////////////
    public Article createArticle(ArticleCreateDto article){
        Article newArticle = articleRepository.save(createDtoToArticle(article));
        inventoryService.createInventories(article.getInventories(),newArticle);
        return newArticle;
    }

    public List<ArticleOrderedDto> getManagerQueueArticles(){
        return itemService.getItemsByStatus(0).stream().map(this::inventoryToArticleOrderDto).collect(Collectors.toList());
    }
    public List<ArticleOrderedDto> getManagerShippingArticles(){
        return itemService.getItemsByStatus(1).stream().map(this::inventoryToArticleOrderDto).collect(Collectors.toList());
    }public List<ArticleOrderedDto> getManagerDeliveredArticles(){
        return itemService.getItemsByStatus(2).stream().map(this::inventoryToArticleOrderDto).collect(Collectors.toList());
    }

    public List<ArticleOrderedDto> getShippingArticlesByUser(){
        return itemService.getCustomerShippigItems().stream().map(this::inventoryToArticleOrderDto).collect(Collectors.toList());
    }
    public List<ArticleOrderedDto> getDeliveredArticlesByUser(){
        User user = userService.getCurrentUser();
        if(user == null)
            return null;
        return itemService.getItemsByStatusAndUser(1).stream().map(this::inventoryToArticleOrderDto).collect(Collectors.toList());
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
    public List<ArticleSellerDto> getSellersArticlesAvailable(){
        User user = this.userService.getCurrentUser();
        if(user == null)
            return new ArrayList<>();
        return articleRepository.getByUserAvailable(user.getId()).stream().map(this::toSellerArticleDto).toList();
    }
    public List<ArticleSellerDto> getSellersArticlesUnavailable(){
        User user = this.userService.getCurrentUser();
        if(user == null)
            return new ArrayList<>();
        return articleRepository.getByUserNotAvailable(user.getId()).stream().map(this::toSellerArticleDto).toList();
    }

    public List<ArticleSellerDto> getArticlesNonActive(){
        return articleRepository.getArticlesNotActive().stream().map(this::toSellerArticleDto).collect(Collectors.toList());
    }

    public void setActive(Long articleId){
        articleRepository.setActive(articleId);
    }


    //////////////////////////////////////////////////////
    ////////////CONVERTERS///////////////////////////////
    //////////////////////////////////////////////////////
    private Article createDtoToArticle(ArticleCreateDto article){
        return Article.builder()
                .color(colorService.getColorById(article.getColor()))
                .product(productService.getProductById(article.getProduct()))
                .price(article.getPrice())
                .available(true)
                .active(false)
                .build();
    }
    private ArticleSellerDto toSellerArticleDto(Article article){
        return ArticleSellerDto
                .builder()
                .id(article.getId())
                .color(article.getColor().getName())
                .mainPic(pictureService.getMainPic(article.getId()))
                .name(article.getProduct().getName())
                .price(article.getPrice())
                .category(article.getProduct().getCategory().getName())
                .inventories(article.getInventory().stream().map(Inventory::getSize).collect(Collectors.toList()))
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
    private ArticleOrderedDto inventoryToArticleOrderDto(Item item){
        Inventory inventory = item.getInventory();
        Address address = item.getOrder().getAddress();
        Article article = inventory.getArticle();
        return ArticleOrderedDto.builder()
                .id(article.getId())
                .size(inventory.getSize())
                .name(article.getProduct().getName())
                .price(article.getPrice())
                .pic(pictureService.getMainPic(article.getId()))
                .address(address.getAddressLine())
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .quantity(item.getQuantity())
                .itemId(item.getId())
                .build();

    }

}
