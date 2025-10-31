package br.com.tp.lncr.kitchenorder.datasources.postgres;

import br.com.tp.lncr.core.dtos.kitchenorder.KitchenOrderDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JpaKitchenOrderRepositoryImplTest {
    private JpaKitchenOrderRepositoryImpl repositoryImpl;
    private JpaKitchenOrderRepository jpaRepository;
    private JpaKitchenOrderMapper mapper;

    @BeforeEach
    void setUp() {
        repositoryImpl = new JpaKitchenOrderRepositoryImpl();
        jpaRepository = mock(JpaKitchenOrderRepository.class);
        mapper = mock(JpaKitchenOrderMapper.class);
    }

    @Test
    void saveKitchenOrderSuccessfully() {
        KitchenOrderDTO inputDTO = new KitchenOrderDTO();
        inputDTO.setId(1);
        inputDTO.setCustomerOrderId(100);

        JpaKitchenOrderEntity entity = new JpaKitchenOrderEntity();
        entity.setId(1);

        JpaKitchenOrderEntity savedEntity = new JpaKitchenOrderEntity();
        savedEntity.setId(1);

        KitchenOrderDTO expectedDTO = new KitchenOrderDTO();
        expectedDTO.setId(1);

        when(mapper.kitchenOrderDTOtoJpa(inputDTO)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(savedEntity);
        when(mapper.jpaKitchenOrderToDTO(savedEntity)).thenReturn(expectedDTO);

        KitchenOrderDTO result = repositoryImpl.save(inputDTO, jpaRepository, mapper);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(mapper).kitchenOrderDTOtoJpa(inputDTO);
        verify(jpaRepository).save(entity);
        verify(mapper).jpaKitchenOrderToDTO(savedEntity);
    }

    @Test
    void findByIdReturnsKitchenOrderWhenExists() {
        Integer id = 1;
        JpaKitchenOrderEntity entity = new JpaKitchenOrderEntity();
        entity.setId(id);

        KitchenOrderDTO expectedDTO = new KitchenOrderDTO();
        expectedDTO.setId(id);

        when(jpaRepository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.jpaKitchenOrderToDTO(entity)).thenReturn(expectedDTO);

        KitchenOrderDTO result = repositoryImpl.findById(id, jpaRepository, mapper);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(jpaRepository).findById(id);
        verify(mapper).jpaKitchenOrderToDTO(entity);
    }

    @Test
    void findByIdReturnsNullWhenNotExists() {
        Integer id = 999;

        when(jpaRepository.findById(id)).thenReturn(Optional.empty());
        when(mapper.jpaKitchenOrderToDTO(null)).thenReturn(null);

        KitchenOrderDTO result = repositoryImpl.findById(id, jpaRepository, mapper);

        assertNull(result);
        verify(jpaRepository).findById(id);
        verify(mapper).jpaKitchenOrderToDTO(null);
    }

    @Test
    void findByStatusIdReturnsListOfKitchenOrders() {
        List<Integer> statusIds = Arrays.asList(1, 2);

        JpaKitchenOrderEntity entity1 = new JpaKitchenOrderEntity();
        entity1.setId(1);
        JpaKitchenOrderEntity entity2 = new JpaKitchenOrderEntity();
        entity2.setId(2);
        List<JpaKitchenOrderEntity> entities = Arrays.asList(entity1, entity2);

        KitchenOrderDTO dto1 = new KitchenOrderDTO();
        dto1.setId(1);
        KitchenOrderDTO dto2 = new KitchenOrderDTO();
        dto2.setId(2);

        when(jpaRepository.findByStatusIdList(statusIds)).thenReturn(entities);
        when(mapper.jpaKitchenOrderToDTO(entity1)).thenReturn(dto1);
        when(mapper.jpaKitchenOrderToDTO(entity2)).thenReturn(dto2);

        List<KitchenOrderDTO> result = repositoryImpl.findByStatusId(statusIds, jpaRepository, mapper);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        verify(jpaRepository).findByStatusIdList(statusIds);
    }

    @Test
    void findByStatusIdReturnsEmptyListWhenNoMatches() {
        List<Integer> statusIds = List.of(999);
        List<JpaKitchenOrderEntity> emptyList = List.of();

        when(jpaRepository.findByStatusIdList(statusIds)).thenReturn(emptyList);

        List<KitchenOrderDTO> result = repositoryImpl.findByStatusId(statusIds, jpaRepository, mapper);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(jpaRepository).findByStatusIdList(statusIds);
    }

    @Test
    void updateStatusByCustomerOrderIdSuccessfully() {
        Integer customerOrderId = 100;
        Integer newStatusId = 2;

        JpaKitchenOrderEntity entity = new JpaKitchenOrderEntity();
        entity.setId(1);
        entity.setCustomerOrderId(customerOrderId);
        entity.setStatusId(1);

        JpaKitchenOrderEntity updatedEntity = new JpaKitchenOrderEntity();
        updatedEntity.setId(1);
        updatedEntity.setCustomerOrderId(customerOrderId);
        updatedEntity.setStatusId(newStatusId);

        KitchenOrderDTO expectedDTO = new KitchenOrderDTO();
        expectedDTO.setId(1);

        when(jpaRepository.findById(customerOrderId)).thenReturn(Optional.of(entity));
        when(jpaRepository.save(entity)).thenReturn(updatedEntity);
        when(mapper.jpaKitchenOrderToDTO(updatedEntity)).thenReturn(expectedDTO);

        KitchenOrderDTO result = repositoryImpl.updateStatusByCustomerOrderId(customerOrderId, newStatusId, jpaRepository, mapper);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(newStatusId, entity.getStatusId());
        verify(jpaRepository).findById(customerOrderId);
        verify(jpaRepository).save(entity);
        verify(mapper).jpaKitchenOrderToDTO(updatedEntity);
    }

    @Test
    void updateStatusByCustomerOrderIdReturnsNullWhenNotFound() {
        Integer customerOrderId = 999;
        Integer newStatusId = 2;

        when(jpaRepository.findById(customerOrderId)).thenReturn(Optional.empty());

        KitchenOrderDTO result = repositoryImpl.updateStatusByCustomerOrderId(customerOrderId, newStatusId, jpaRepository, mapper);

        assertNull(result);
        verify(jpaRepository).findById(customerOrderId);
        verify(jpaRepository, never()).save(any());
        verify(mapper, never()).jpaKitchenOrderToDTO(any());
    }

    @Test
    void findByCustomerOrderIdReturnsKitchenOrder() {
        Integer customerOrderId = 100;

        JpaKitchenOrderEntity entity = new JpaKitchenOrderEntity();
        entity.setId(1);
        entity.setCustomerOrderId(customerOrderId);

        KitchenOrderDTO expectedDTO = new KitchenOrderDTO();
        expectedDTO.setId(1);
        expectedDTO.setCustomerOrderId(customerOrderId);

        when(jpaRepository.findByCustomerOrderId(customerOrderId)).thenReturn(entity);
        when(mapper.jpaKitchenOrderToDTO(entity)).thenReturn(expectedDTO);

        KitchenOrderDTO result = repositoryImpl.findByCustomerOrderId(customerOrderId, jpaRepository, mapper);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(customerOrderId, result.getCustomerOrderId());
        verify(jpaRepository).findByCustomerOrderId(customerOrderId);
        verify(mapper).jpaKitchenOrderToDTO(entity);
    }

    @Test
    void findByCustomerOrderIdReturnsNullWhenNotFound() {
        Integer customerOrderId = 999;

        when(jpaRepository.findByCustomerOrderId(customerOrderId)).thenReturn(null);
        when(mapper.jpaKitchenOrderToDTO(null)).thenReturn(null);

        KitchenOrderDTO result = repositoryImpl.findByCustomerOrderId(customerOrderId, jpaRepository, mapper);

        assertNull(result);
        verify(jpaRepository).findByCustomerOrderId(customerOrderId);
        verify(mapper).jpaKitchenOrderToDTO(null);
    }
}
