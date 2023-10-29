package shopapi.shopapi.service.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shopapi.shopapi.models.product.Article;
import shopapi.shopapi.models.product.Picture;
import shopapi.shopapi.repository.product.PictureRepository;
import shopapi.shopapi.storage.FileStorageService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PictureService {
    private final PictureRepository pictureRepository;
    private final FileStorageService storageService;
    public void createPictures(List<MultipartFile> files,Article article){

        for(MultipartFile file:files){
            createPicture(file,article);
        }
    }
    public void createMainPic(MultipartFile file,Article article){
        String name = storageService.save(file);
        pictureRepository.save(Picture.builder()
                .article(article)
                .name(name)
                .main(true)
                .build());
    }
    public void createPicture(MultipartFile file, Article article){
        String name = storageService.save(file);
        pictureRepository.save(Picture.builder()
                        .article(article)
                        .name(name)
                        .main(false)
                        .build());
    }
    public String getMainPic(Long articleId){
        return pictureRepository.getMainPic(articleId);
    }
    public List<String> getPics(Long articleId){
        return pictureRepository.getPics(articleId);
    }
}
