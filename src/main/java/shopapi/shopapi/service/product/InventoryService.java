package shopapi.shopapi.service.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopapi.shopapi.dto.inventory.InventoryCreateDto;
import shopapi.shopapi.dto.inventory.InventoryDto;
import shopapi.shopapi.dto.inventory.InventoryItemOrderDto;
import shopapi.shopapi.dto.inventory.InventoryUpdateDto;
import shopapi.shopapi.models.product.Article;
import shopapi.shopapi.models.product.Inventory;
import shopapi.shopapi.repository.product.InventoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public void updateInventories(List<InventoryUpdateDto> inventoryUpdateDtoList, Article article){
        List<Inventory> inventories = article.getInventory();
        List<Long> inventoryIds = new ArrayList<>();
        Inventory inventory;
        for(InventoryUpdateDto item:inventoryUpdateDtoList){
            if(item.getId() == null){
                inventory = inventoryRepository.save(updateDtoToInventory(item,article));
                inventoryIds.add(inventory.getId());
            }
            else{
                inventory = updateDtoToInventory(item,article);
                inventory.setAvailable(true);
                inventory.setId(item.getId());
                inventoryRepository.save(inventory);
                inventoryIds.add(item.getId());
            }
        }
        inventoryRepository.setUnavailableByArticleId(inventoryIds, article.getId());

//        for(int i=0;i<inventories.size()-1;i++){
//            for(int j=0;j<inventoryIds.size()-1;j++){
//                if(Objects.equals(inventories.get(i).getId(), inventoryIds.get(j))){
//                    System.out.println("Removing item with id="+inventories.get(i).getId());
//                    inventories.remove(i);
//                }
//            }
//        }
//        for(Inventory item:inventories){
//            System.out.println("Setting available false item with id="+item.getId());
//            item.setAvailable(false);
//        }
//        inventoryRepository.saveAll(inventories);

//        List<Inventory> inventories = article.getInventory();
//        List<Long> inventoryIds = new ArrayList<>();
//        for(InventoryUpdateDto item:inventoryUpdateDtoList){
//            if(item.getId() == null){
//                inventoryRepository.save(updateDtoToInventory(item,article));
//            }
//            else{
//                Inventory inventory = updateDtoToInventory(item,article);
//                inventory.setAvailable(true);
//                inventory.setId(item.getId());
//                inventoryRepository.save(inventory);
//                inventoryIds.add(item.getId());
//            }
//        }
//        for(int i=0;i<inventories.size()-1;i++){
//            for(int j=0;j<inventoryIds.size()-1;j++){
//                if(Objects.equals(inventories.get(i).getId(), inventoryIds.get(j))){
//                    inventories.remove(i);
//                }
//            }
//        }
//        for(Inventory item:inventories){
//            item.setAvailable(false);
//        }
//        inventoryRepository.saveAll(inventories);
    }
    public List<InventoryDto> getInventoryDTOsByArticle(Long id){
        return inventoryRepository.getByArticleId(id).stream().map(this::inventoryToDto).collect(Collectors.toList());
    }
    public List<Inventory> getShippingInventoriesByUser(Long userId){
        return inventoryRepository.findShippingInventoriesByUser(userId);
    }
    public List<InventoryItemOrderDto> getQueueInventories(){
        System.out.println("so far fine");
        return inventoryRepository.findQueueInventories();
    }
    public List<Inventory> getShippingInventories(){
        return inventoryRepository.findShippingInventories();
    }
    public List<Inventory> getDeliveredInventories(){
        return inventoryRepository.findDeliveredInventories();
    }
    public List<Inventory> getDeliveredInventoriesByUser(Long userId){
        return inventoryRepository.findDeliveredInventoriesByUser(userId);
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
    private Inventory updateDtoToInventory(InventoryUpdateDto inventoryUpdateDto,Article article){
        return Inventory.builder()
                .article(article)
                .available(true)
                .size(inventoryUpdateDto.getSize())
                .build();
    }
}
