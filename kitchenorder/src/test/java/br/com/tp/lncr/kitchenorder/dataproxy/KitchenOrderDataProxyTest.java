package br.com.tp.lncr.kitchenorder.dataproxy;

import br.com.tp.lncr.commons.integrations.customerorder.CustomerOrderIntegrationImpl;
import br.com.tp.lncr.commons.integrations.notifcation.NotificationIntegraionImpl;
import br.com.tp.lncr.core.dtos.kitchenorder.KitchenOrderDTO;
import br.com.tp.lncr.core.dtos.kitchenorder.KitchenOrderFoodItemDTO;
import br.com.tp.lncr.kitchenorder.datasources.postgres.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KitchenOrderDataProxyTest {

    @Mock
    private JpaKitchenOrderRepositoryImpl jpaKitchenOrderRepositoryImpl;
    @Mock
    private JpaKitchenOrderRepository jpaKitchenOrderRepository;
    @Mock
    private JpaKitchenOrderFoodItemRepositoryImpl jpaKitchenOrderFoodItemRepositoryImpl;
    @Mock
    private JpaKitchenOrderFoodItemRepository jpaKitchenOrderFoodItemRepository;
    @Mock
    private CustomerOrderIntegrationImpl customerOrderIntegrationImpl;
    @Mock
    private NotificationIntegraionImpl notificationIntegraion;
    @Mock
    private JpaKitchenOrderMapper jpaKitchenOrderMapper;

    private KitchenOrderDataProxy kitchenOrderDataProxy;

    @BeforeEach
    void setUp() {
        kitchenOrderDataProxy = new KitchenOrderDataProxy(
                jpaKitchenOrderRepositoryImpl,
                jpaKitchenOrderRepository,
                jpaKitchenOrderFoodItemRepositoryImpl,
                jpaKitchenOrderFoodItemRepository,
                customerOrderIntegrationImpl,
                notificationIntegraion,
                jpaKitchenOrderMapper
        );
    }

    @Test
    void shouldFindByIdWithoutFoodItemsWhenCalledWithSingleParameter() {
        Integer kitchenOrderId = 1;
        KitchenOrderDTO expectedOrder = createKitchenOrderDTO(1);

        when(jpaKitchenOrderRepositoryImpl.findById(kitchenOrderId, jpaKitchenOrderRepository, jpaKitchenOrderMapper))
                .thenReturn(expectedOrder);

        KitchenOrderDTO result = kitchenOrderDataProxy.findById(kitchenOrderId);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(jpaKitchenOrderRepositoryImpl).findById(kitchenOrderId, jpaKitchenOrderRepository, jpaKitchenOrderMapper);
        verify(jpaKitchenOrderFoodItemRepositoryImpl, never()).findByKitchenOrderId(any(), any(), any());
    }

    @Test
    void shouldFindByIdWithoutFoodItems() {
        Integer kitchenOrderId = 1;
        Boolean includeFoodItems = false;
        KitchenOrderDTO expectedOrder = createKitchenOrderDTO(1);

        when(jpaKitchenOrderRepositoryImpl.findById(kitchenOrderId, jpaKitchenOrderRepository, jpaKitchenOrderMapper))
                .thenReturn(expectedOrder);

        KitchenOrderDTO result = kitchenOrderDataProxy.findById(kitchenOrderId, includeFoodItems);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(jpaKitchenOrderRepositoryImpl).findById(kitchenOrderId, jpaKitchenOrderRepository, jpaKitchenOrderMapper);
        verify(jpaKitchenOrderFoodItemRepositoryImpl, never()).findByKitchenOrderId(any(), any(), any());
    }

    @Test
    void shouldFindByIdWithFoodItems() {
        Integer kitchenOrderId = 1;
        Boolean includeFoodItems = true;
        KitchenOrderDTO expectedOrder = createKitchenOrderDTO(1);
        List<KitchenOrderFoodItemDTO> foodItems = List.of(new KitchenOrderFoodItemDTO());

        when(jpaKitchenOrderRepositoryImpl.findById(kitchenOrderId, jpaKitchenOrderRepository, jpaKitchenOrderMapper))
                .thenReturn(expectedOrder);
        when(jpaKitchenOrderFoodItemRepositoryImpl.findByKitchenOrderId(kitchenOrderId, jpaKitchenOrderFoodItemRepository, jpaKitchenOrderMapper))
                .thenReturn(foodItems);

        KitchenOrderDTO result = kitchenOrderDataProxy.findById(kitchenOrderId, includeFoodItems);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertNotNull(result.getFoodItems());
        assertEquals(1, result.getFoodItems().size());
        verify(jpaKitchenOrderRepositoryImpl).findById(kitchenOrderId, jpaKitchenOrderRepository, jpaKitchenOrderMapper);
        verify(jpaKitchenOrderFoodItemRepositoryImpl).findByKitchenOrderId(kitchenOrderId, jpaKitchenOrderFoodItemRepository, jpaKitchenOrderMapper);
    }

    @Test
    void shouldNotIncludeFoodItemsWhenOrderIsNull() {
        Integer kitchenOrderId = 999;
        Boolean includeFoodItems = true;

        when(jpaKitchenOrderRepositoryImpl.findById(kitchenOrderId, jpaKitchenOrderRepository, jpaKitchenOrderMapper))
                .thenReturn(null);

        KitchenOrderDTO result = kitchenOrderDataProxy.findById(kitchenOrderId, includeFoodItems);

        assertNull(result);
        verify(jpaKitchenOrderRepositoryImpl).findById(kitchenOrderId, jpaKitchenOrderRepository, jpaKitchenOrderMapper);
        verify(jpaKitchenOrderFoodItemRepositoryImpl, never()).findByKitchenOrderId(any(), any(), any());
    }

    @Test
    void shouldFindByCustomerOrderIdWithoutFoodItems() {
        Integer customerOrderId = 1;
        Boolean includeFoodItems = false;
        KitchenOrderDTO expectedOrder = createKitchenOrderDTO(1);

        when(jpaKitchenOrderRepositoryImpl.findByCustomerOrderId(customerOrderId, jpaKitchenOrderRepository, jpaKitchenOrderMapper))
                .thenReturn(expectedOrder);

        KitchenOrderDTO result = kitchenOrderDataProxy.findByCustomerOrderId(customerOrderId, includeFoodItems);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(jpaKitchenOrderRepositoryImpl).findByCustomerOrderId(customerOrderId, jpaKitchenOrderRepository, jpaKitchenOrderMapper);
        verify(jpaKitchenOrderFoodItemRepositoryImpl, never()).findByKitchenOrderId(any(), any(), any());
    }

    @Test
    void shouldFindByCustomerOrderIdWithFoodItems() {
        Integer customerOrderId = 1;
        Boolean includeFoodItems = true;
        KitchenOrderDTO expectedOrder = createKitchenOrderDTO(1);
        List<KitchenOrderFoodItemDTO> foodItems = List.of(new KitchenOrderFoodItemDTO());

        when(jpaKitchenOrderRepositoryImpl.findByCustomerOrderId(customerOrderId, jpaKitchenOrderRepository, jpaKitchenOrderMapper))
                .thenReturn(expectedOrder);
        when(jpaKitchenOrderFoodItemRepositoryImpl.findByKitchenOrderId(1, jpaKitchenOrderFoodItemRepository, jpaKitchenOrderMapper))
                .thenReturn(foodItems);

        KitchenOrderDTO result = kitchenOrderDataProxy.findByCustomerOrderId(customerOrderId, includeFoodItems);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertNotNull(result.getFoodItems());
        assertEquals(1, result.getFoodItems().size());
        verify(jpaKitchenOrderRepositoryImpl).findByCustomerOrderId(customerOrderId, jpaKitchenOrderRepository, jpaKitchenOrderMapper);
        verify(jpaKitchenOrderFoodItemRepositoryImpl).findByKitchenOrderId(1, jpaKitchenOrderFoodItemRepository, jpaKitchenOrderMapper);
    }

    @Test
    void shouldFindByStatusListWithoutFoodItems() {
        List<Integer> statusIds = Arrays.asList(1, 2);
        Boolean includeFoodItems = false;
        List<KitchenOrderDTO> expectedOrders = Arrays.asList(
                createKitchenOrderDTO(1),
                createKitchenOrderDTO(2)
        );

        when(jpaKitchenOrderRepositoryImpl.findByStatusId(statusIds, jpaKitchenOrderRepository, jpaKitchenOrderMapper))
                .thenReturn(expectedOrders);

        List<KitchenOrderDTO> result = kitchenOrderDataProxy.findByStatusList(statusIds, includeFoodItems);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jpaKitchenOrderRepositoryImpl).findByStatusId(statusIds, jpaKitchenOrderRepository, jpaKitchenOrderMapper);
        verify(jpaKitchenOrderFoodItemRepositoryImpl, never()).findByKitchenOrderId(any(), any(), any());
    }

    @Test
    void shouldFindByStatusListWithFoodItems() {
        List<Integer> statusIds = Arrays.asList(1, 2);
        Boolean includeFoodItems = true;
        List<KitchenOrderDTO> expectedOrders = Arrays.asList(
                createKitchenOrderDTO(1),
                createKitchenOrderDTO(2)
        );
        List<KitchenOrderFoodItemDTO> foodItems1 = List.of(new KitchenOrderFoodItemDTO());
        List<KitchenOrderFoodItemDTO> foodItems2 = List.of(new KitchenOrderFoodItemDTO());

        when(jpaKitchenOrderRepositoryImpl.findByStatusId(statusIds, jpaKitchenOrderRepository, jpaKitchenOrderMapper))
                .thenReturn(expectedOrders);
        when(jpaKitchenOrderFoodItemRepositoryImpl.findByKitchenOrderId(1, jpaKitchenOrderFoodItemRepository, jpaKitchenOrderMapper))
                .thenReturn(foodItems1);
        when(jpaKitchenOrderFoodItemRepositoryImpl.findByKitchenOrderId(2, jpaKitchenOrderFoodItemRepository, jpaKitchenOrderMapper))
                .thenReturn(foodItems2);

        List<KitchenOrderDTO> result = kitchenOrderDataProxy.findByStatusList(statusIds, includeFoodItems);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertNotNull(result.get(0).getFoodItems());
        assertNotNull(result.get(1).getFoodItems());
        verify(jpaKitchenOrderRepositoryImpl).findByStatusId(statusIds, jpaKitchenOrderRepository, jpaKitchenOrderMapper);
        verify(jpaKitchenOrderFoodItemRepositoryImpl, times(2)).findByKitchenOrderId(any(), eq(jpaKitchenOrderFoodItemRepository), eq(jpaKitchenOrderMapper));
    }

    @Test
    void shouldFindFoodItemsByKitchenOrderId() {
        Integer kitchenOrderId = 1;
        List<KitchenOrderFoodItemDTO> expectedFoodItems = Arrays.asList(
                new KitchenOrderFoodItemDTO(),
                new KitchenOrderFoodItemDTO()
        );

        when(jpaKitchenOrderFoodItemRepositoryImpl.findByKitchenOrderId(kitchenOrderId, jpaKitchenOrderFoodItemRepository, jpaKitchenOrderMapper))
                .thenReturn(expectedFoodItems);

        List<KitchenOrderFoodItemDTO> result = kitchenOrderDataProxy.findByKitchenOrderId(kitchenOrderId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jpaKitchenOrderFoodItemRepositoryImpl).findByKitchenOrderId(kitchenOrderId, jpaKitchenOrderFoodItemRepository, jpaKitchenOrderMapper);
    }

    @Test
    void shouldSaveKitchenOrderWithoutFoodItems() {
        KitchenOrderDTO kitchenOrderDto = createKitchenOrderDTO(null);
        kitchenOrderDto.setFoodItems(null);
        KitchenOrderDTO savedOrder = createKitchenOrderDTO(1);

        when(jpaKitchenOrderRepositoryImpl.save(kitchenOrderDto, jpaKitchenOrderRepository, jpaKitchenOrderMapper))
                .thenReturn(savedOrder);

        KitchenOrderDTO result = kitchenOrderDataProxy.save(kitchenOrderDto);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(jpaKitchenOrderRepositoryImpl).save(kitchenOrderDto, jpaKitchenOrderRepository, jpaKitchenOrderMapper);
        verify(jpaKitchenOrderFoodItemRepositoryImpl, never()).saveAll(any(), any(), any());
    }

    @Test
    void shouldSaveKitchenOrderWithEmptyFoodItems() {
        KitchenOrderDTO kitchenOrderDto = createKitchenOrderDTO(null);
        kitchenOrderDto.setFoodItems(List.of());
        KitchenOrderDTO savedOrder = createKitchenOrderDTO(1);

        when(jpaKitchenOrderRepositoryImpl.save(kitchenOrderDto, jpaKitchenOrderRepository, jpaKitchenOrderMapper))
                .thenReturn(savedOrder);

        KitchenOrderDTO result = kitchenOrderDataProxy.save(kitchenOrderDto);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(jpaKitchenOrderRepositoryImpl).save(kitchenOrderDto, jpaKitchenOrderRepository, jpaKitchenOrderMapper);
        verify(jpaKitchenOrderFoodItemRepositoryImpl, never()).saveAll(any(), any(), any());
    }

    @Test
    void shouldSaveKitchenOrderWithFoodItems() {
        KitchenOrderFoodItemDTO foodItem = new KitchenOrderFoodItemDTO();
        KitchenOrderDTO kitchenOrderDto = createKitchenOrderDTO(null);
        kitchenOrderDto.setFoodItems(List.of(foodItem));

        KitchenOrderDTO savedOrder = createKitchenOrderDTO(1);
        savedOrder.setFoodItems(List.of(foodItem));

        KitchenOrderFoodItemDTO savedFoodItem = new KitchenOrderFoodItemDTO();
        savedFoodItem.setKitchenOrderId(1);

        when(jpaKitchenOrderRepositoryImpl.save(kitchenOrderDto, jpaKitchenOrderRepository, jpaKitchenOrderMapper))
                .thenReturn(savedOrder);
        when(jpaKitchenOrderFoodItemRepositoryImpl.saveAll(any(), eq(jpaKitchenOrderFoodItemRepository), eq(jpaKitchenOrderMapper)))
                .thenReturn(List.of(savedFoodItem));

        KitchenOrderDTO result = kitchenOrderDataProxy.save(kitchenOrderDto);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertNotNull(result.getFoodItems());
        assertEquals(1, result.getFoodItems().size());
        assertEquals(1, result.getFoodItems().get(0).getKitchenOrderId());
        verify(jpaKitchenOrderRepositoryImpl).save(kitchenOrderDto, jpaKitchenOrderRepository, jpaKitchenOrderMapper);
        verify(jpaKitchenOrderFoodItemRepositoryImpl).saveAll(any(), eq(jpaKitchenOrderFoodItemRepository), eq(jpaKitchenOrderMapper));
    }

    @Test
    void shouldSendNotificationWithValidParameters() {
        String notificationType = "ORDER_READY";
        Integer artefactId = 1;
        String message = "Order is ready for pickup";

        kitchenOrderDataProxy.sendNotification(notificationType, artefactId, message);

        verify(notificationIntegraion).sendNotification(notificationType, artefactId, message);
    }

    @Test
    void shouldUpdateCustomerOrderStatus() {
        Integer customerOrderId = 1;
        String status = "PREPARING";

        kitchenOrderDataProxy.updateCustomerOrderStatus(customerOrderId, status);

        verify(customerOrderIntegrationImpl).updateCustomerOrderStatus(customerOrderId, status);
    }

    private KitchenOrderDTO createKitchenOrderDTO(Integer id) {
        KitchenOrderDTO dto = new KitchenOrderDTO();
        dto.setId(id);
        return dto;
    }
}
