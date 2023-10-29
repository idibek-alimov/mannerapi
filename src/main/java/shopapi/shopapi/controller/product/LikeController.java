package shopapi.shopapi.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import shopapi.shopapi.service.product.LikeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
public class LikeController {
    private final LikeService likeService;
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public void likeArticle(@PathVariable("id") Long id){
        System.out.println("started liking");
        likeService.likeArticle(id);
    }
    @GetMapping("/liked/pictures")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<String> findPicsOfLiked(){
        return likeService.findPicLiked();
    }

    @GetMapping("/create/extension")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public void createExtension(){
        likeService.createExtension();
    }


}
