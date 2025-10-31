package br.com.tp.lncr.kitchenorder.datasources.postgres;

import br.com.tp.lncr.core.dtos.kitchenorder.KitchenOrderFoodItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JpaKitchenOrderFoodItemRepositoryImplTest {
    private JpaKitchenOrderFoodItemRepositoryImpl repositoryImpl;
    private JpaKitchenOrderFoodItemRepository jpaRepository;
    private JpaKitchenOrderMapper mapper;

    @BeforeEach
    void setUp() {
        repositoryImpl = new JpaKitchenOrderFoodItemRepositoryImpl();
        jpaRepository = mock(JpaKitchenOrderFoodItemRepository.class);
        mapper = mock(JpaKitchenOrderMapper.class);
    }

    @Test
    void saveAllFoodItemsSuccessfully() {
        KitchenOrderFoodItemDTO dto1 = new KitchenOrderFoodItemDTO();
        dto1.setId(1);
        dto1.setName("Pizza");

        KitchenOrderFoodItemDTO dto2 = new KitchenOrderFoodItemDTO();
        dto2.setId(2);
        dto2.setName("Burger");

        List<KitchenOrderFoodItemDTO> inputDTOs = Arrays.asList(dto1, dto2);

        JpaKitchenOrderFoodItemEntity entity1 = new JpaKitchenOrderFoodItemEntity();
        entity1.setId(1);
        entity1.setName("Pizza");

        JpaKitchenOrderFoodItemEntity entity2 = new JpaKitchenOrderFoodItemEntity();
        entity2.setId(2);
        entity2.setName("Burger");

        List<JpaKitchenOrderFoodItemEntity> entities = Arrays.asList(entity1, entity2);
        List<JpaKitchenOrderFoodItemEntity> savedEntities = Arrays.asList(entity1, entity2);

        when(mapper.kitchenOrderFoodItemDtoToJpa(dto1)).thenReturn(entity1);
        when(mapper.kitchenOrderFoodItemDtoToJpa(dto2)).thenReturn(entity2);
        when(jpaRepository.saveAll(entities)).thenReturn(savedEntities);
        when(mapper.jpaKitchenOrderFoodItemToDTO(entity1)).thenReturn(dto1);
        when(mapper.jpaKitchenOrderFoodItemToDTO(entity2)).thenReturn(dto2);

        List<KitchenOrderFoodItemDTO> result = repositoryImpl.saveAll(inputDTOs, jpaRepository, mapper);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Pizza", result.get(0).getName());
        assertEquals("Burger", result.get(1).getName());
        verify(jpaRepository).saveAll(entities);
        verify(mapper, times(2)).kitchenOrderFoodItemDtoToJpa(any());
        verify(mapper, times(2)).jpaKitchenOrderFoodItemToDTO(any());
    }

    @Test
    void saveAllWithEmptyListReturnsEmptyList() {
        List<KitchenOrderFoodItemDTO> emptyDTOs = Collections.emptyList();
        List<JpaKitchenOrderFoodItemEntity> emptyEntities = Collections.emptyList();

        when(jpaRepository.saveAll(emptyEntities)).thenReturn(emptyEntities);

        List<KitchenOrderFoodItemDTO> result = repositoryImpl.saveAll(emptyDTOs, jpaRepository, mapper);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(jpaRepository).saveAll(emptyEntities);
    }

    @Test
    void saveAllWithSingleItemSuccessfully() {
        KitchenOrderFoodItemDTO dto = new KitchenOrderFoodItemDTO();
        dto.setId(1);
        dto.setKitchenOrderId(10);
        dto.setName("Pasta");
        dto.setDescription("Carbonara");
        dto.setNotes("Extra cheese");

        List<KitchenOrderFoodItemDTO> inputDTOs = Collections.singletonList(dto);

        JpaKitchenOrderFoodItemEntity entity = new JpaKitchenOrderFoodItemEntity();
        entity.setId(1);
        entity.setKitchenOrderId(10);
        entity.setName("Pasta");

        List<JpaKitchenOrderFoodItemEntity> entities = Collections.singletonList(entity);
        List<JpaKitchenOrderFoodItemEntity> savedEntities = Collections.singletonList(entity);

        when(mapper.kitchenOrderFoodItemDtoToJpa(dto)).thenReturn(entity);
        when(jpaRepository.saveAll(entities)).thenReturn(savedEntities);
        when(mapper.jpaKitchenOrderFoodItemToDTO(entity)).thenReturn(dto);

        List<KitchenOrderFoodItemDTO> result = repositoryImpl.saveAll(inputDTOs, jpaRepository, mapper);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(10, result.get(0).getKitchenOrderId());
        assertEquals("Pasta", result.get(0).getName());
        verify(jpaRepository).saveAll(entities);
    }

    @Test
    void findByKitchenOrderIdReturnsMatchingItems() {
        Integer kitchenOrderId = 100;

        JpaKitchenOrderFoodItemEntity entity1 = new JpaKitchenOrderFoodItemEntity();
        entity1.setId(1);
        entity1.setKitchenOrderId(kitchenOrderId);
        entity1.setName("Pizza");

        JpaKitchenOrderFoodItemEntity entity2 = new JpaKitchenOrderFoodItemEntity();
        entity2.setId(2);
        entity2.setKitchenOrderId(kitchenOrderId);
        entity2.setName("Salad");

        List<JpaKitchenOrderFoodItemEntity> entities = Arrays.asList(entity1, entity2);

        KitchenOrderFoodItemDTO dto1 = new KitchenOrderFoodItemDTO();
        dto1.setId(1);
        dto1.setKitchenOrderId(kitchenOrderId);
        dto1.setName("Pizza");

        KitchenOrderFoodItemDTO dto2 = new KitchenOrderFoodItemDTO();
        dto2.setId(2);
        dto2.setKitchenOrderId(kitchenOrderId);
        dto2.setName("Salad");

        when(jpaRepository.findByKitchenOrderId(kitchenOrderId)).thenReturn(entities);
        when(mapper.jpaKitchenOrderFoodItemToDTO(entity1)).thenReturn(dto1);
        when(mapper.jpaKitchenOrderFoodItemToDTO(entity2)).thenReturn(dto2);

        List<KitchenOrderFoodItemDTO> result = repositoryImpl.findByKitchenOrderId(kitchenOrderId, jpaRepository, mapper);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Pizza", result.get(0).getName());
        assertEquals("Salad", result.get(1).getName());
        assertEquals(kitchenOrderId, result.get(0).getKitchenOrderId());
        assertEquals(kitchenOrderId, result.get(1).getKitchenOrderId());
        verify(jpaRepository).findByKitchenOrderId(kitchenOrderId);
        verify(mapper, times(2)).jpaKitchenOrderFoodItemToDTO(any());
    }

    @Test
    void findByKitchenOrderIdReturnsEmptyListWhenNoItems() {
        Integer kitchenOrderId = 999;
        List<JpaKitchenOrderFoodItemEntity> emptyEntities = Collections.emptyList();

        when(jpaRepository.findByKitchenOrderId(kitchenOrderId)).thenReturn(emptyEntities);

        List<KitchenOrderFoodItemDTO> result = repositoryImpl.findByKitchenOrderId(kitchenOrderId, jpaRepository, mapper);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(jpaRepository).findByKitchenOrderId(kitchenOrderId);
        verify(mapper, never()).jpaKitchenOrderFoodItemToDTO(any());
    }

    @Test
    void findByKitchenOrderIdReturnsSingleItem() {
        Integer kitchenOrderId = 50;

        JpaKitchenOrderFoodItemEntity entity = new JpaKitchenOrderFoodItemEntity();
        entity.setId(1);
        entity.setKitchenOrderId(kitchenOrderId);
        entity.setName("Dessert");
        entity.setDescription("Chocolate Cake");
        entity.setNotes("No nuts");

        List<JpaKitchenOrderFoodItemEntity> entities = Collections.singletonList(entity);

        KitchenOrderFoodItemDTO dto = new KitchenOrderFoodItemDTO();
        dto.setId(1);
        dto.setKitchenOrderId(kitchenOrderId);
        dto.setName("Dessert");
        dto.setDescription("Chocolate Cake");
        dto.setNotes("No nuts");

        when(jpaRepository.findByKitchenOrderId(kitchenOrderId)).thenReturn(entities);
        when(mapper.jpaKitchenOrderFoodItemToDTO(entity)).thenReturn(dto);

        List<KitchenOrderFoodItemDTO> result = repositoryImpl.findByKitchenOrderId(kitchenOrderId, jpaRepository, mapper);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Dessert", result.get(0).getName());
        assertEquals("Chocolate Cake", result.get(0).getDescription());
        assertEquals("No nuts", result.get(0).getNotes());
        verify(jpaRepository).findByKitchenOrderId(kitchenOrderId);
        verify(mapper).jpaKitchenOrderFoodItemToDTO(entity);
    }

    @Test
    void repositoryMethodsHandleNullMapperResults() {
        Integer kitchenOrderId = 100;

        JpaKitchenOrderFoodItemEntity entity = new JpaKitchenOrderFoodItemEntity();
        entity.setId(1);

        List<JpaKitchenOrderFoodItemEntity> entities = Collections.singletonList(entity);

        when(jpaRepository.findByKitchenOrderId(kitchenOrderId)).thenReturn(entities);
        when(mapper.jpaKitchenOrderFoodItemToDTO(entity)).thenReturn(null);

        List<KitchenOrderFoodItemDTO> result = repositoryImpl.findByKitchenOrderId(kitchenOrderId, jpaRepository, mapper);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertNull(result.get(0));
        verify(jpaRepository).findByKitchenOrderId(kitchenOrderId);
        verify(mapper).jpaKitchenOrderFoodItemToDTO(entity);
    }
}
