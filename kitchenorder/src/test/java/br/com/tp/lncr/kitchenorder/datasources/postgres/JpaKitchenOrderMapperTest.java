package br.com.tp.lncr.kitchenorder.datasources.postgres;

import br.com.tp.lncr.core.dtos.kitchenorder.KitchenOrderDTO;
import br.com.tp.lncr.core.dtos.kitchenorder.KitchenOrderFoodItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class JpaKitchenOrderMapperTest {
    private JpaKitchenOrderMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new JpaKitchenOrderMapper();
    }

    @Test
    void jpaKitchenOrderToDTOConvertsSuccessfully() {
        LocalDateTime now = LocalDateTime.now();

        JpaKitchenOrderFoodItemEntity foodItemEntity = new JpaKitchenOrderFoodItemEntity();
        foodItemEntity.setId(1);
        foodItemEntity.setKitchenOrderId(10);
        foodItemEntity.setName("Pizza");
        foodItemEntity.setDescription("Mussarela");
        foodItemEntity.setNotes("Sem cebola");

        JpaKitchenOrderEntity entity = new JpaKitchenOrderEntity();
        entity.setId(10);
        entity.setCustomerOrderId(100);
        entity.setStatusId(1);
        entity.setCreated(now);
        entity.setUpdated(now);
        entity.setFoodItems(Collections.singletonList(foodItemEntity));

        KitchenOrderDTO result = mapper.jpaKitchenOrderToDTO(entity);

        assertNotNull(result);
        assertEquals(10, result.getId());
        assertEquals(100, result.getCustomerOrderId());
        assertEquals("Received", result.getStatus());
        assertEquals(now, result.getCreated());
        assertEquals(now, result.getUpdated());
        assertNotNull(result.getFoodItems());
        assertEquals(1, result.getFoodItems().size());
        assertEquals("Pizza", result.getFoodItems().get(0).getName());
    }

    @Test
    void jpaKitchenOrderToDTOReturnsNullWhenEntityIsNull() {
        KitchenOrderDTO result = mapper.jpaKitchenOrderToDTO(null);
        assertNull(result);
    }

    @Test
    void jpaKitchenOrderToDTOHandlesNullFoodItems() {
        JpaKitchenOrderEntity entity = new JpaKitchenOrderEntity();
        entity.setId(10);
        entity.setCustomerOrderId(100);
        entity.setStatusId(2);
        entity.setFoodItems(null);

        KitchenOrderDTO result = mapper.jpaKitchenOrderToDTO(entity);

        assertNotNull(result);
        assertEquals(10, result.getId());
        assertEquals(100, result.getCustomerOrderId());
        assertEquals("Preparing", result.getStatus());
        assertNull(result.getFoodItems());
    }

    @Test
    void kitchenOrderDTOtoJpaConvertsSuccessfully() {
        LocalDateTime now = LocalDateTime.now();

        KitchenOrderFoodItemDTO foodItemDTO = new KitchenOrderFoodItemDTO();
        foodItemDTO.setId(1);
        foodItemDTO.setKitchenOrderId(10);
        foodItemDTO.setName("Burger");
        foodItemDTO.setDescription("Cheese Burger");
        foodItemDTO.setNotes("Extra cheese");

        KitchenOrderDTO dto = new KitchenOrderDTO();
        dto.setId(10);
        dto.setCustomerOrderId(100);
        dto.setStatus("Ready");
        dto.setCreated(now);
        dto.setUpdated(now);
        dto.setFoodItems(Collections.singletonList(foodItemDTO));

        JpaKitchenOrderEntity result = mapper.kitchenOrderDTOtoJpa(dto);

        assertNotNull(result);
        assertEquals(10, result.getId());
        assertEquals(100, result.getCustomerOrderId());
        assertEquals(3, result.getStatusId());
        assertEquals(now, result.getCreated());
        assertEquals(now, result.getUpdated());
        assertNotNull(result.getFoodItems());
        assertEquals(1, result.getFoodItems().size());
        assertEquals("Burger", result.getFoodItems().get(0).getName());
    }

    @Test
    void kitchenOrderDTOtoJpaReturnsNullWhenDTOIsNull() {
        JpaKitchenOrderEntity result = mapper.kitchenOrderDTOtoJpa(null);
        assertNull(result);
    }

    @Test
    void kitchenOrderDTOtoJpaHandlesNullFoodItems() {
        KitchenOrderDTO dto = new KitchenOrderDTO();
        dto.setId(10);
        dto.setCustomerOrderId(100);
        dto.setStatus("Finished");
        dto.setFoodItems(null);

        JpaKitchenOrderEntity result = mapper.kitchenOrderDTOtoJpa(dto);

        assertNotNull(result);
        assertEquals(10, result.getId());
        assertEquals(100, result.getCustomerOrderId());
        assertEquals(4, result.getStatusId());
        assertNull(result.getFoodItems());
    }

    @Test
    void jpaKitchenOrderFoodItemToDTOConvertsSuccessfully() {
        JpaKitchenOrderFoodItemEntity entity = new JpaKitchenOrderFoodItemEntity();
        entity.setId(5);
        entity.setKitchenOrderId(15);
        entity.setName("Salad");
        entity.setDescription("Caesar Salad");
        entity.setNotes("No croutons");

        KitchenOrderFoodItemDTO result = mapper.jpaKitchenOrderFoodItemToDTO(entity);

        assertNotNull(result);
        assertEquals(5, result.getId());
        assertEquals(15, result.getKitchenOrderId());
        assertEquals("Salad", result.getName());
        assertEquals("Caesar Salad", result.getDescription());
        assertEquals("No croutons", result.getNotes());
    }

    @Test
    void jpaKitchenOrderFoodItemToDTOReturnsNullWhenEntityIsNull() {
        KitchenOrderFoodItemDTO result = mapper.jpaKitchenOrderFoodItemToDTO(null);
        assertNull(result);
    }

    @Test
    void kitchenOrderFoodItemDtoToJpaConvertsSuccessfully() {
        KitchenOrderFoodItemDTO dto = new KitchenOrderFoodItemDTO();
        dto.setId(7);
        dto.setKitchenOrderId(20);
        dto.setName("Pasta");
        dto.setDescription("Carbonara");
        dto.setNotes("Extra bacon");

        JpaKitchenOrderFoodItemEntity result = mapper.kitchenOrderFoodItemDtoToJpa(dto);

        assertNotNull(result);
        assertEquals(7, result.getId());
        assertEquals(20, result.getKitchenOrderId());
        assertEquals("Pasta", result.getName());
        assertEquals("Carbonara", result.getDescription());
        assertEquals("Extra bacon", result.getNotes());
    }

    @Test
    void kitchenOrderFoodItemDtoToJpaReturnsNullWhenDTOIsNull() {
        JpaKitchenOrderFoodItemEntity result = mapper.kitchenOrderFoodItemDtoToJpa(null);
        assertNull(result);
    }

    @Test
    void mapperHandlesAllKitchenOrderStatuses() {
        JpaKitchenOrderEntity entity = new JpaKitchenOrderEntity();
        entity.setId(1);
        entity.setStatusId(1);
        assertEquals("Received", mapper.jpaKitchenOrderToDTO(entity).getStatus());

        entity.setStatusId(2);
        assertEquals("Preparing", mapper.jpaKitchenOrderToDTO(entity).getStatus());

        entity.setStatusId(3);
        assertEquals("Ready", mapper.jpaKitchenOrderToDTO(entity).getStatus());

        entity.setStatusId(4);
        assertEquals("Finished", mapper.jpaKitchenOrderToDTO(entity).getStatus());
    }

    @Test
    void mapperHandlesMultipleFoodItems() {
        JpaKitchenOrderFoodItemEntity item1 = new JpaKitchenOrderFoodItemEntity();
        item1.setId(1);
        item1.setName("Item 1");

        JpaKitchenOrderFoodItemEntity item2 = new JpaKitchenOrderFoodItemEntity();
        item2.setId(2);
        item2.setName("Item 2");

        JpaKitchenOrderEntity entity = new JpaKitchenOrderEntity();
        entity.setId(10);
        entity.setStatusId(1);
        entity.setFoodItems(Arrays.asList(item1, item2));

        KitchenOrderDTO result = mapper.jpaKitchenOrderToDTO(entity);

        assertNotNull(result.getFoodItems());
        assertEquals(2, result.getFoodItems().size());
        assertEquals("Item 1", result.getFoodItems().get(0).getName());
        assertEquals("Item 2", result.getFoodItems().get(1).getName());
    }
}
