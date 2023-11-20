package shopapi.shopapi.service.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shopapi.shopapi.dto.article.*;
import shopapi.shopapi.dto.color.ColorDto;
import shopapi.shopapi.dto.inventory.InventoryItemOrderDto;
import shopapi.shopapi.dto.inventory.InventoryUpdateDto;
import shopapi.shopapi.models.order.Item;
import shopapi.shopapi.models.product.Article;
import shopapi.shopapi.models.product.Category;
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
    @Value("${application.bucket.url}")
    private String bucketURL;
    @Value("${application.bucket.name}")
    private String bucketName;

    ///////////////////////////////////////////////////
    ///////////////BASIC SHIT///////////////////////////
    //////////////////////////////////////////////////////

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
    public List<ArticleDto> getArticles(Integer page,Integer amount){
        return articleRepository.findAll(PageRequest.of(page,amount, Sort.by("created_at").descending())).stream().map(this::articleToDto).collect(Collectors.toList());
    }
    public List<ArticleDto> searchByName(String text,Integer page,Integer amount){
        return articleRepository.searchArticles(text,PageRequest.of(page,amount, Sort.by("created_at").descending())).stream().map(this::articleToDto).collect(Collectors.toList());
    }
    public ArticleDetailDto getArticleById(Long id){
        Optional<Article> optionalArticle = articleRepository.findById(id);
        return optionalArticle.map(this::articleToDetailDto).orElse(null);
    }
    public Article getActualArticleById(Long id){
        return articleRepository.findById(id).orElse(null);
    }
    public List<ArticleDto> getByCategory(Long id,Integer page,Integer amount){
        return articleRepository.getByCategory(id,PageRequest.of(page,amount, Sort.by("created_at").descending())).stream().map(this::articleToDto).collect(Collectors.toList());
    }
    public List<ArticleColorDto> getOtherColors(Long id){
        return articleRepository.findOtherColors(id).stream().map(this::articleToArticleColorDto).collect(Collectors.toList());
    }
    public void changeArticleAvailable(Long id,Integer status){
        User user = userService.getCurrentUser();
        if(user== null){
            return;
        }
        System.out.println("article id="+id+" and user id="+user.getId());
        switch (status){
            case 0:
                articleRepository.setAvailable(id,user.getId());
            case 1:
                articleRepository.setUnavailable(id,user.getId());
            default:
                break;
        }
    }
    ///////////////////////////////////////////////////////////////////
    /////////////////////////////CREATE AND THING///////////////////////
    ////////////////////////////////////////////////////////////////////
    public Article createArticle(ArticleCreateDto article){
        Article newArticle = articleRepository.save(createDtoToArticle(article));
        inventoryService.createInventories(article.getInventories(),newArticle);
        return newArticle;
    }
    public void createArticleWithPictures(ArticleCreateDto articleDto,List<MultipartFile> files,MultipartFile mainPic){
        Article article = createArticle(articleDto);
        pictureService.createPictures(files,article);
        pictureService.createMainPic(mainPic,article);

    }
    public void updateArticle(Article article,ArticleUpdateDto articleUpdateDto){
            //Article article = articleOptional.get();
            article.setColor(colorService.getColorById(articleUpdateDto.getColor()));
            article.setPrice(articleUpdateDto.getPrice());
            article.setDiscount(articleUpdateDto.getDiscount());
            inventoryService.updateInventories(articleUpdateDto.getInventories(),article);
    }
    public Article updateArticleWithPictures(ArticleUpdateDto articleUpdateDto,
                                          List<MultipartFile> files,
                                          List<String> oldPics){
        Optional<Article> articleOptional = articleRepository.findById(articleUpdateDto.getId());
        if(articleOptional.isPresent()){
            Article article = articleOptional.get();
            updateArticle(article,articleUpdateDto);
            pictureService.updatePictures(oldPics,article);
            pictureService.createPictures(files,article);
            return article;
        }
        return null;
    }
    public Article updateArticleWithoutNewPictures(ArticleUpdateDto articleUpdateDto,
                                             List<String> oldPics){
        Optional<Article> articleOptional = articleRepository.findById(articleUpdateDto.getId());
        if(articleOptional.isPresent()){
            Article article = articleOptional.get();
            updateArticle(article,articleUpdateDto);
            pictureService.updatePictures(oldPics,article);
            //pictureService.createPictures(files,article);
            return article;
        }
        return null;
    }

    public void updateArticleWithPicturesMain(ArticleUpdateDto articleUpdateDto,
                                              List<MultipartFile> files,
                                              List<String> oldPics,
                                              MultipartFile mainPic){

        Article article = updateArticleWithPictures(articleUpdateDto,files,oldPics);
        if(article !=null){
            pictureService.updateMainPic(article,mainPic);
        }
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
    public List<ArticleSellerUpdateDto> getArticlesByProduct(Long id){
        return articleRepository.getArticlesByProductId(id).stream().map(this::toArticleSellerUpdateDto).collect(Collectors.toList());
    }
    public void setActive(Long articleId){
        articleRepository.setActive(articleId);
    }
    public void deactivateArticle(Long articleId){
        articleRepository.deactivateArticle(articleId);
    }


    //////////////////////////////////////////////////////
    ////////////CONVERTERS///////////////////////////////
    //////////////////////////////////////////////////////
    private Article createDtoToArticle(ArticleCreateDto article){
        return Article.builder()
                .color(colorService.getColorById(article.getColor()))
                .product(productService.getProductById(article.getProduct()))
                .price(article.getPrice())
                .discount(article.getDiscount())
                .available(true)
                .active(false)
                .build();
    }
    private ArticleSellerDto toSellerArticleDto(Article article){
        Category category = article.getProduct().getCategory();
        return ArticleSellerDto
                .builder()
                .id(article.getId())
                .productId(article.getProduct().getId())
                .color(article.getColor().getName())
                .mainPic(bucketURL+"/"+bucketName+"/"+pictureService.getMainPic(article.getId()))
                .name(article.getProduct().getName())
                .price(article.getPrice())
                .discount(article.getDiscount())
                .category(category != null ? category.getName() : null)
                .inventories(article.getInventory().stream().map(Inventory::getSize).collect(Collectors.toList()))
                .build();
    }
    private ArticleDto articleToDto(Article article){
        return ArticleDto.builder()
                .id(article.getId())
                .name(article.getProduct().getName())
                .price(article.getDiscount() != null ? getDiscountPrice(article.getPrice(),article.getDiscount()): article.getPrice())
                .prevPrice(article.getDiscount() != null ? article.getPrice() : null)
                .discount(article.getDiscount())
                .pic(bucketURL+"/"+bucketName+"/"+pictureService.getMainPic(article.getId()))
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
                .mainPic(bucketURL+"/"+bucketName+"/"+pictureService.getMainPic(article.getId()))
                .pics(pictureService.getPics(article.getId()).stream().map(item->bucketURL+"/"+bucketName+"/"+item).collect(Collectors.toList()))
                .price(article.getDiscount() != null ? getDiscountPrice(article.getPrice(),article.getDiscount()): article.getPrice())
                .discount(article.getDiscount())
                .prevPrice(article.getDiscount() != null ? article.getPrice() : null)
                .color(article.getColor().getName())
                .inventories(inventoryService.getInventoryDTOsByArticle(article.getId()))
                .like(userService.getCurrentUser() != null && articleRepository.findLikedByUserAndArticle(userService.getCurrentUser().getId(), article.getId()))
                .build();
    }
    private ArticleColorDto articleToArticleColorDto(Article article){
       return ArticleColorDto.builder()
                .id(article.getId())
                .mainPic(bucketURL+"/"+bucketName+"/"+pictureService.getMainPic(article.getId()))
                .build();
    }
    private ArticleOrderedDto inventoryToArticleOrderDto(Item item){
        Inventory inventory = item.getInventory();
        Address address = item.getOrder().getAddress();
        Article article = inventory.getArticle();

        return ArticleOrderedDto.builder()
                .id(article.getId())
                .size(inventory.getSize())
                .prevPrice(article.getDiscount() != null ? article.getPrice() : null)
                .name(article.getProduct().getName())
                .price(article.getDiscount() != null ? getDiscountPrice(article.getPrice(),article.getDiscount()):article.getPrice())
                .pic(bucketURL+"/"+bucketName+"/"+pictureService.getMainPic(article.getId()))
                .discount(article.getDiscount())
                .address(address.getAddressLine())
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .quantity(item.getQuantity())
                .itemId(item.getId())
                .build();

    }
    private Double getDiscountPrice(Double price,Integer discount){
        return Math.floor((double)(price*((100-discount)/100.0f)));
    }
    private ArticleSellerUpdateDto toArticleSellerUpdateDto(Article article){
        return ArticleSellerUpdateDto.builder()
                .id(article.getId())
                .color(article.getColor() != null ? ColorDto.builder().id(article.getColor().getId()).name(article.getColor().getName()).build():null)
                .price(article.getPrice())
                .discount(article.getDiscount())
                .inventories(article.getInventory().stream().filter(Inventory::getAvailable).map(inventory -> InventoryUpdateDto.builder().id(inventory.getId()).size(inventory.getSize()).build()).collect(Collectors.toList()))
                .pictures(article.getPictures().stream().filter(item->!item.getMain()).map(item->bucketURL+"/"+bucketName+"/"+item.getName()).collect(Collectors.toList()))
                .mainPic(bucketURL+"/"+bucketName+"/"+pictureService.getMainPic(article.getId()))
                .product(article.getProduct().getId())
                .build();
    }
}
