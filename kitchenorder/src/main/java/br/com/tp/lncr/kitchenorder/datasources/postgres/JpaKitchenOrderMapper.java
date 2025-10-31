package br.com.tp.lncr.kitchenorder.datasources.postgres;

import br.com.tp.lncr.core.dtos.kitchenorder.KitchenOrderDTO;
import br.com.tp.lncr.core.dtos.kitchenorder.KitchenOrderFoodItemDTO;
import br.com.tp.lncr.core.enums.KitchenOrderStatus;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class JpaKitchenOrderMapper {
    public KitchenOrderDTO jpaKitchenOrderToDTO(JpaKitchenOrderEntity entity) {
        if (entity == null) return null;
        KitchenOrderDTO dto = new KitchenOrderDTO();
        dto.setId(entity.getId());
        dto.setCustomerOrderId(entity.getCustomerOrderId());
        dto.setStatus(KitchenOrderStatus.fromId(entity.getStatusId()).getDescription());
        dto.setCreated(entity.getCreated());
        dto.setUpdated(entity.getUpdated());
        if (entity.getFoodItems() != null) {
            dto.setFoodItems(entity.getFoodItems().stream()
                .map(this::jpaKitchenOrderFoodItemToDTO)
                .collect(Collectors.toList()));
        }
        return dto;
    }

    public JpaKitchenOrderEntity kitchenOrderDTOtoJpa(KitchenOrderDTO dto) {
        if (dto == null) return null;
        JpaKitchenOrderEntity entity = new JpaKitchenOrderEntity();
        entity.setId(dto.getId());
        entity.setCustomerOrderId(dto.getCustomerOrderId());
        entity.setStatusId(KitchenOrderStatus.fromDescription(dto.getStatus()).getId());
        entity.setCreated(dto.getCreated());
        entity.setUpdated(dto.getUpdated());
        if (dto.getFoodItems() != null) {
            entity.setFoodItems(dto.getFoodItems().stream()
                .map(this::kitchenOrderFoodItemDtoToJpa)
                .collect(Collectors.toList()));
        }
        return entity;
    }

    public KitchenOrderFoodItemDTO jpaKitchenOrderFoodItemToDTO(JpaKitchenOrderFoodItemEntity entity) {
        if (entity == null) return null;
        return new KitchenOrderFoodItemDTO(
            entity.getId(),
            entity.getKitchenOrderId(),
            entity.getName(),
            entity.getDescription(),
            entity.getNotes()
        );
    }

    public JpaKitchenOrderFoodItemEntity kitchenOrderFoodItemDtoToJpa(KitchenOrderFoodItemDTO dto) {
        if (dto == null) return null;
        return new JpaKitchenOrderFoodItemEntity(
            dto.getId(),
            dto.getKitchenOrderId(),
            dto.getName(),
            dto.getDescription(),
            dto.getNotes()
        );
    }
}
