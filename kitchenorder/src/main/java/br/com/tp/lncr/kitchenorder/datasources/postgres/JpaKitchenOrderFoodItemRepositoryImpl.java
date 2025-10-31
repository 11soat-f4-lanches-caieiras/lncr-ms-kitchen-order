package br.com.tp.lncr.kitchenorder.datasources.postgres;

import br.com.tp.lncr.core.dtos.kitchenorder.KitchenOrderFoodItemDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaKitchenOrderFoodItemRepositoryImpl {

    public List<KitchenOrderFoodItemDTO> saveAll(List<KitchenOrderFoodItemDTO> foodItemsDTOList, JpaKitchenOrderFoodItemRepository jpaKitchenOrderFoodItemRepository, JpaKitchenOrderMapper jpaKitchenOrderMapper) {
        List<JpaKitchenOrderFoodItemEntity> jpaKitchenOrderFoodItemList = foodItemsDTOList.stream()
                .map(jpaKitchenOrderMapper::kitchenOrderFoodItemDtoToJpa)
                .toList();
        jpaKitchenOrderFoodItemList = jpaKitchenOrderFoodItemRepository.saveAll(jpaKitchenOrderFoodItemList);
        return jpaKitchenOrderFoodItemList.stream().map(jpaKitchenOrderMapper::jpaKitchenOrderFoodItemToDTO).toList();

    }

    public List<KitchenOrderFoodItemDTO> findByKitchenOrderId(Integer kitchenOrderId, JpaKitchenOrderFoodItemRepository jpaKitchenOrderFoodItemRepository, JpaKitchenOrderMapper jpaKitchenOrderMapper) {
        List<JpaKitchenOrderFoodItemEntity> jpaKitchenOrderFoodItemList = jpaKitchenOrderFoodItemRepository.findByKitchenOrderId(kitchenOrderId);
        return jpaKitchenOrderFoodItemList.stream()
                .map(jpaKitchenOrderMapper::jpaKitchenOrderFoodItemToDTO)
                .toList();
    }
}
