package shopapi.shopapi.storage;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStorageServiceInterface {
    public void init();
    public String save(MultipartFile file);
    public Resource load(String filename);
    public void deleteAll();
    public Stream<Path> loadAll();
}
