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

    //////////////////////////////////////////////////////////////////////////////
    ////////////////////////BASIC SHIT ////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    @GetMapping("/{page}/{amount}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<ArticleDto> getArticles(@PathVariable("page")Integer page,
                                        @PathVariable("amount")Integer amount){
        return articleService.getArticles(page, amount);
    }
    @GetMapping("/search/{text}/{page}/{amount}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<ArticleDto> findArticles(@PathVariable("text")String text,
                                         @PathVariable("page")Integer page,
                                         @PathVariable("amount")Integer amount){
        return articleService.searchByName(text,page,amount);
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
    @GetMapping("/category/{id}/{page}/{amount}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<ArticleDto> getByCategory(@PathVariable("id") Long id,
                                          @PathVariable("page")Integer page,
                                          @PathVariable("amount")Integer amount){
        return articleService.getByCategory(id,page,amount);
    }

    @GetMapping("/user/liked")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<ArticleDto> getLikedArticles(){
        return articleService.findLikedArticles();
    }
    ///////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////USER ORDER SHIT //////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
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

    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////SELLERS AND MANAGER SHIT ////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////


                            ////////////////////////////////////////////////////
                            /////////////MANAGER SHIT/////////////////////////
                            /////////////////////////////////////////////////
    @GetMapping("/seller/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<ArticleSellerUpdateDto> getArticlesByProduct(@PathVariable("id")Long id){
        return articleService.getArticlesByProduct(id);
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

    @GetMapping("/seller/set/available/{id}/{status}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public void setArticleAvailable(@PathVariable("id")Long id,
                                    @PathVariable("status")Integer status){
        articleService.changeArticleAvailable(id,status);
    }



    ////////////////////////////////////////////////////
                            /////////////MANAGER SHIT/////////////////////////
                            /////////////////////////////////////////////////

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


    ///////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////CREATE,UPDATE AND SHIT //////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
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
    @PostMapping(path="/seller/update/pictures/main",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin("*")
    private void updateArticleWithPicturesMain(@RequestPart("article") ArticleUpdateDto article,
                                           @RequestPart("newPictures") List<MultipartFile> files,
                                           @RequestPart("oldPictures") List<String> oldPics,
                                           @RequestPart("mainPic") MultipartFile mainFile){
        articleService.updateArticleWithPicturesMain(article,files,oldPics,mainFile);
    }
    @PostMapping(path="/seller/update",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin("*")
    private void updateArticleWithOutPictures(@RequestPart("article") ArticleUpdateDto article,
                                           //@RequestPart("newPictures") List<MultipartFile> files,
                                           @RequestPart("oldPictures") List<String> oldPics){
        //articleService.createArticleWithPictures(article,files,mainFile);
        articleService.updateArticleWithoutNewPictures(article,oldPics);
    }
    @PostMapping(path="/seller/update/pictures",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin("*")
    private void updateArticleWithPictures(@RequestPart("article") ArticleUpdateDto article,
                                           @RequestPart("newPictures") List<MultipartFile> files,
                                           @RequestPart("oldPictures") List<String> oldPics){
        //articleService.createArticleWithPictures(article,files,mainFile);
        articleService.updateArticleWithPictures(article,files,oldPics);
    }
}
