package shopapi.shopapi.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class FileStorageService {
    public String save(MultipartFile file) {
        //this.init();
        Random random = new Random();
        String extension = file.getOriginalFilename().split("\\.")[1];
        String new_name = String.format("%s%s",System.currentTimeMillis(),random.nextInt(100000)+"."+extension);
        return new_name;
    }
}
