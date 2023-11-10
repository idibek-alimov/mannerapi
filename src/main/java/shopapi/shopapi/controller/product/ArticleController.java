package shopapi.shopapi.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shopapi.shopapi.dto.article.*;
import shopapi.shopapi.service.product.ArticleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
public class ArticleController {
    private final ArticleService articleService;
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<ArticleDto> getArticles(){
        return articleService.getArticles();
    }
    @GetMapping("/search/{text}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<ArticleDto> findArticles(@PathVariable("text")String text){
        return articleService.searchByName(text);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public ArticleDetailDto getArticleById(@PathVariable("id")Long id){
        return articleService.getArticleById(id);
    }
    @GetMapping("/colors/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<ArticleColorDto> getOtherColors(@PathVariable("id") Long id){
        return articleService.getOtherColors(id);
    }
    @GetMapping("/category/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<ArticleDto> getByCategory(@PathVariable("id") Long id){
        return articleService.getByCategory(id);
    }

    @GetMapping("/user/liked")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<ArticleDto> getLikedArticles(){
        return articleService.findLikedArticles();
    }


    @PostMapping(path="/seller/create",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin("*")
    private void createArticle(@RequestPart("article") ArticleCreateDto article){
        articleService.createArticle(article);
    }
    @PostMapping(path="/seller/create/pictures",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin("*")
    private void createArticleWithPictures(@RequestPart("article") ArticleCreateDto article,
                                           @RequestPart("pictures") List<MultipartFile> files,
                                           @RequestPart("mainPic") MultipartFile mainFile){
        articleService.createArticleWithPictures(article,files,mainFile);
    }
    @GetMapping("/ordered/shipping")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<ArticleOrderedDto> getShippingArticles(){
        return articleService.getShippingArticlesByUser();
    }
    @GetMapping("/ordered/delivered")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<ArticleOrderedDto> getDeliveredArticles(){
        return articleService.getDeliveredArticlesByUser();
    }

    @GetMapping("/seller/available")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<ArticleSellerDto> getArticlesAvailable(){
        return articleService.getSellersArticlesAvailable();
    }

    @GetMapping("/seller/unavailable")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<ArticleSellerDto> getArticlesUnavailable(){
        return articleService.getSellersArticlesUnavailable();
    }

    @GetMapping("/manager/nonactive")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<ArticleSellerDto> getArticlesNonactive(){
        return articleService.getArticlesNonActive();
    }
    @GetMapping("/manager/activate/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public void getArticlesNonactive(@PathVariable("id")Long id){
        articleService.setActive(id);
    }
    @GetMapping("/manager/order/queue")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<ArticleOrderedDto> getManagerQueueArticles(){
        return articleService.getManagerQueueArticles();
    }
    @GetMapping("/manager/order/shipping")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<ArticleOrderedDto> getManagerShippingArticles(){
        return articleService.getManagerShippingArticles();
    }
    @GetMapping("/manager/order/delivered")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<ArticleOrderedDto> getManagerDeliveredArticles(){
        return articleService.getManagerDeliveredArticles();
    }
}
