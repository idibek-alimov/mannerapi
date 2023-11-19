package shopapi.shopapi.service.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shopapi.shopapi.models.product.Article;
import shopapi.shopapi.models.product.Picture;
import shopapi.shopapi.repository.product.PictureRepository;
import shopapi.shopapi.storage.FileStorageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class PictureService {
    private final PictureRepository pictureRepository;
    private final FileStorageService storageService;
    public void createPictures(List<MultipartFile> files,Article article){
        System.out.println("Creating new pictures");
        System.out.println(files);
        for(MultipartFile file:files){
            createPicture(file,article);
        }
    }
    public void updatePictures(List<String> oldPics,Article article){
        System.out.println("Article id="+article.getId());
        List<Picture> pictures = article.getPictures();
        List<Long> ids = new ArrayList<>();
        System.out.println("size of pictures="+pictures.size());
        for(int i=0;i<pictures.size();i++){
            System.out.println("current checking picture");
            System.out.println(pictures.get(i).getName());
            if(containsPicture(pictures.get(i).getName(),oldPics)){
                ids.add(pictures.get(i).getId());
            }
        }
        pictureRepository.deleteNotIncluding(ids);

//        System.out.println("not present pics");
//        for(Picture pic:pictures){
//            System.out.println(pic.getName());
//            pictureRepository.deleteById(pic.getId());
//        }
    }
    public void updateMainPic(Article article,MultipartFile pic){
        pictureRepository.deleteMainPicture(article.getId());
        createMainPic(pic,article);
    }
    public Boolean containsPicture(String name,List<String> namesList){
        System.out.println("namesList");
        System.out.println(namesList);
        for(int i=0;i<namesList.size();i++){
            System.out.println("currently comparing "+name + " with "+namesList.get(i));
            if(Objects.equals(name, namesList.get(i))){
                return true;
            }
        }
        return false;
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
        System.out.println("Created this pic="+name);
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
