package br.com.tp.lncr.kitchenorder.apis;

import br.com.tp.lncr.kitchenorder.apis.KitchenOrderRestControllerImpl;
import br.com.tp.lncr.commons.model.ResponseListModel;
import br.com.tp.lncr.commons.model.ResponseModel;
import br.com.tp.lncr.kitchenorder.configs.KitchenOrderConfig;
import br.com.tp.lncr.kitchenorder.dataproxy.KitchenOrderDataProxy;
import br.com.tp.lncr.core.dtos.kitchenorder.KitchenOrderDTO;
import br.com.tp.lncr.core.interfaces.kitchenorder.KitchenOrderController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KitchenOrderRestControllerImplTest {

    @Mock
    private KitchenOrderController kitchenOrderController;

    @Mock
    private KitchenOrderDataProxy kitchenOrderDataProxy;

    @Mock
    private KitchenOrderConfig kitchenOrderConfig;

    @InjectMocks
    private KitchenOrderRestControllerImpl kitchenOrderRestController;

    private KitchenOrderDTO kitchenOrderDTO;

    @BeforeEach
    void setUp() {
        kitchenOrderDTO = new KitchenOrderDTO();
        kitchenOrderDTO.setId(1);
        kitchenOrderDTO.setCustomerOrderId(1);
        kitchenOrderDTO.setStatus("RECEIVED");
        kitchenOrderDTO.setCreated(LocalDateTime.now());
    }

    @Test
    void deveCriarKitchenOrderComSucesso() {
        when(kitchenOrderController.createKitchenOrder(kitchenOrderDTO)).thenReturn(kitchenOrderDTO);
        when(kitchenOrderConfig.getLocationPrefix()).thenReturn("/kitchenOrders");

        ResponseEntity<ResponseModel<KitchenOrderDTO>> response = kitchenOrderRestController.createKitchenOrder(kitchenOrderDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNull(response.getBody().getContent());
        assertNotNull(response.getHeaders().getLocation());
        assertTrue(response.getHeaders().getLocation().toString().contains("/kitchenOrders/1"));
        verify(kitchenOrderController).createKitchenOrder(kitchenOrderDTO);
    }

    @Test
    void deveRetornarKitchenOrderPorIdSemFoodItems() {
        when(kitchenOrderController.getKitchenOrderById(1, false)).thenReturn(kitchenOrderDTO);

        ResponseEntity<ResponseModel<KitchenOrderDTO>> response = kitchenOrderRestController.getKitchenOrderById(1, false);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(kitchenOrderDTO, response.getBody().getContent());
        verify(kitchenOrderController).getKitchenOrderById(1, false);
    }

    @Test
    void deveRetornarKitchenOrderPorIdComFoodItems() {
        when(kitchenOrderController.getKitchenOrderById(1, true)).thenReturn(kitchenOrderDTO);

        ResponseEntity<ResponseModel<KitchenOrderDTO>> response = kitchenOrderRestController.getKitchenOrderById(1, true);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(kitchenOrderDTO, response.getBody().getContent());
        verify(kitchenOrderController).getKitchenOrderById(1, true);
    }

    @Test
    void deveRetornarKitchenOrderPorCustomerOrderIdSemFoodItems() {
        when(kitchenOrderController.getKitchenOrderByCustomerOrderId(1, false)).thenReturn(kitchenOrderDTO);

        ResponseEntity<ResponseModel<KitchenOrderDTO>> response = kitchenOrderRestController.getKitchenOrderByCustomerOrderId(1, false);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(kitchenOrderDTO, response.getBody().getContent());
        verify(kitchenOrderController).getKitchenOrderByCustomerOrderId(1, false);
    }

    @Test
    void deveRetornarKitchenOrderPorCustomerOrderIdComFoodItems() {
        when(kitchenOrderController.getKitchenOrderByCustomerOrderId(1, true)).thenReturn(kitchenOrderDTO);

        ResponseEntity<ResponseModel<KitchenOrderDTO>> response = kitchenOrderRestController.getKitchenOrderByCustomerOrderId(1, true);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(kitchenOrderDTO, response.getBody().getContent());
        verify(kitchenOrderController).getKitchenOrderByCustomerOrderId(1, true);
    }

    @Test
    void deveRetornarKitchenOrdersPorStatusListSemFoodItems() {
        List<String> statusList = Arrays.asList("RECEIVED", "PREPARING");
        List<KitchenOrderDTO> orders = Collections.singletonList(kitchenOrderDTO);
        when(kitchenOrderController.getKitchenOrderByStatusList(statusList, false)).thenReturn(orders);

        ResponseEntity<ResponseListModel<KitchenOrderDTO>> response = kitchenOrderRestController.getKitchenOrderByStatusList(statusList, false);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(orders, response.getBody().getContent());
        verify(kitchenOrderController).getKitchenOrderByStatusList(statusList, false);
    }

    @Test
    void deveRetornarKitchenOrdersPorStatusListComFoodItems() {
        List<String> statusList = List.of("READY");
        List<KitchenOrderDTO> orders = Collections.singletonList(kitchenOrderDTO);
        when(kitchenOrderController.getKitchenOrderByStatusList(statusList, true)).thenReturn(orders);

        ResponseEntity<ResponseListModel<KitchenOrderDTO>> response = kitchenOrderRestController.getKitchenOrderByStatusList(statusList, true);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(orders, response.getBody().getContent());
        verify(kitchenOrderController).getKitchenOrderByStatusList(statusList, true);
    }

    @Test
    void deveAtualizarStatusKitchenOrderSemForceUpdateEComUpdateCustomerOrder() {
        KitchenOrderDTO updatedOrder = new KitchenOrderDTO();
        updatedOrder.setId(1);
        updatedOrder.setStatus("PREPARING");
        when(kitchenOrderController.updateOrderStatusById(1, "PREPARING", false, true)).thenReturn(updatedOrder);

        ResponseEntity<ResponseModel<KitchenOrderDTO>> response = kitchenOrderRestController.updateOrderStatusById(1, "PREPARING", false, true);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedOrder, response.getBody().getContent());
        verify(kitchenOrderController).updateOrderStatusById(1, "PREPARING", false, true);
    }

    @Test
    void deveAtualizarStatusKitchenOrderComForceUpdateESemUpdateCustomerOrder() {
        KitchenOrderDTO updatedOrder = new KitchenOrderDTO();
        updatedOrder.setId(1);
        updatedOrder.setStatus("CANCELLED");
        when(kitchenOrderController.updateOrderStatusById(1, "CANCELLED", true, false)).thenReturn(updatedOrder);

        ResponseEntity<ResponseModel<KitchenOrderDTO>> response = kitchenOrderRestController.updateOrderStatusById(1, "CANCELLED", true, false);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedOrder, response.getBody().getContent());
        verify(kitchenOrderController).updateOrderStatusById(1, "CANCELLED", true, false);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverKitchenOrdersComStatus() {
        List<String> statusList = List.of("NONEXISTENT");
        List<KitchenOrderDTO> emptyOrders = List.of();
        when(kitchenOrderController.getKitchenOrderByStatusList(statusList, false)).thenReturn(emptyOrders);

        ResponseEntity<ResponseListModel<KitchenOrderDTO>> response = kitchenOrderRestController.getKitchenOrderByStatusList(statusList, false);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getContent().isEmpty());
        verify(kitchenOrderController).getKitchenOrderByStatusList(statusList, false);
    }

    @Test
    void deveAtualizarStatusComParametrosPadrao() {
        KitchenOrderDTO updatedOrder = new KitchenOrderDTO();
        updatedOrder.setId(1);
        updatedOrder.setStatus("READY");
        when(kitchenOrderController.updateOrderStatusById(1, "READY", false, true)).thenReturn(updatedOrder);

        ResponseEntity<ResponseModel<KitchenOrderDTO>> response = kitchenOrderRestController.updateOrderStatusById(1, "READY", false, true);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedOrder, response.getBody().getContent());
        verify(kitchenOrderController).updateOrderStatusById(1, "READY", false, true);
    }
}
