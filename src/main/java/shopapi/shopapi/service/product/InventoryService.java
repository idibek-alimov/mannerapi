package shopapi.shopapi.service.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopapi.shopapi.dto.inventory.InventoryCreateDto;
import shopapi.shopapi.dto.inventory.InventoryDto;
import shopapi.shopapi.models.product.Article;
import shopapi.shopapi.models.product.Inventory;
import shopapi.shopapi.repository.product.InventoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    public Inventory getById(Long id){
        return inventoryRepository.findById(id).orElse(null);
    }
    public void createInventories(List<InventoryCreateDto> inventories,Article article){
        for(InventoryCreateDto item:inventories){
           createInventory(item,article);
        }
    }
    public void createInventory(InventoryCreateDto inventoryCreateDto,Article article){
        inventoryRepository.save(createDtoToInventory(inventoryCreateDto,article));
    }
    public List<InventoryDto> getInventoryDTOsByArticle(Long id){
        return inventoryRepository.getByArticleId(id).stream().map(this::inventoryToDto).collect(Collectors.toList());
    }
    public List<Inventory> getShippingInventories(Long userId){
        return inventoryRepository.findShippingInventories(userId);
    }
    public List<Inventory> getDeliveredInventories(Long userId){
        return inventoryRepository.findDeliveredInventories(userId);
    }
    private InventoryDto inventoryToDto(Inventory inventory){
        return InventoryDto.builder()
                .id(inventory.getId())
                .available(inventory.getAvailable())
                .size(inventory.getSize())
                .build();
    }
    private Inventory createDtoToInventory(InventoryCreateDto inventoryCreateDto, Article article){
        return Inventory.builder()
                .article(article)
                .available(true)
                .size(inventoryCreateDto.getSize())
                .build();
    }
}
